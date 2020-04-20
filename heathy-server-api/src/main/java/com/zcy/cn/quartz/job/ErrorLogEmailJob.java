package com.zcy.cn.quartz.job;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zcy.cn.bean.Logs;
import com.zcy.cn.enums.LogEnum;
import com.zcy.cn.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Error leavel log send email to zhuchaoyang_1@163.com
 * 支持日志级别修改
 */
@Slf4j
public class ErrorLogEmailJob implements Job {

    // 邮件发送者
    @Value("${spring.mail.username}")
    private String sendMail;

    // 日志级别
    @Value("${email.log.leavel}")
    private String emailLogLeavel;

    @Value("${email.server}")
    private String callBackServer;

    @Autowired
    private LogService logService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("发送{}运维邮件，当前时间点：{}", emailLogLeavel, LocalDateTime.now());
        List<LogEnum> logEnums = Arrays.asList(LogEnum.values()).stream().filter(var -> var.getLeavel().equals(emailLogLeavel.toUpperCase())).collect(Collectors.toList());
        List<Map<String, List<Logs>>> logsResult = new ArrayList<>();    // Key --> 日志级别  value --> 查询Logs对应集合

        // 闭包中只能使用对象方式 用于空包发送邮件告警
        Boolean[] isNeedSendEmail = new Boolean[1];
        isNeedSendEmail[0] = false;

        if (!CollectionUtils.isEmpty(logEnums)) {
            LogEnum fromYml = logEnums.get(0);
            List<LogEnum> greaterThanLeavels = Arrays.asList(LogEnum.values()).stream().filter(var -> fromYml.getIndex() <= var.getIndex()).collect(Collectors.toList());

            greaterThanLeavels.forEach(var -> {
                Map<String, List<Logs>> currentLeavelLogs = new HashMap<>();
                // 直接对持久态的数据进行修改为已发送邮件
                List<Logs> noSenderLogs = logService.findAllByLeavelAndHasSendEmail(var.getLeavel(), false).getContent();
                isNeedSendEmail[0] = !noSenderLogs.isEmpty();
                this.changePersist(noSenderLogs);
                currentLeavelLogs.put(var.getLeavel(), noSenderLogs);
                logsResult.add(currentLeavelLogs);
            });
        }

        // Build HTML sgement. Use StringBuffer for performance, Because splicing is often needed
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<table style=\"width: 100%;border:solid #add9c0;text-align:center;font-size: 14px;border-width:1px 0px 0px 1px;\">");
        stringBuffer.append("<tr>");
        stringBuffer.append("<th style=\"border:solid #add9c0; border-width:0px 1px 1px 0px;\">日志时间</th>");
        stringBuffer.append("<th style=\"border:solid #add9c0; border-width:0px 1px 1px 0px;\">日志级别</th>");
        stringBuffer.append("<th style=\"border:solid #add9c0; border-width:0px 1px 1px 0px;\">触发人员ID</th>");
        stringBuffer.append("<th style=\"border:solid #add9c0; border-width:0px 1px 1px 0px;\">日志简讯</th>");
        stringBuffer.append("</tr>");

        if (!CollectionUtils.isEmpty(logsResult)) {
            for (Map<String, List<Logs>> maps : logsResult) {
                for (Map.Entry entry : maps.entrySet()) {
                    for (Logs logs : (List<Logs>) entry.getValue()) {
                        stringBuffer.append("<tr>");
                        // 级别发生时间
                        this.appendSegment(stringBuffer, false, logs.getCreateTime(), null);
                        // 级别
                        this.appendSegment(stringBuffer, false, logs.getLeavel(), null);
                        // 触发人员ID
                        this.appendSegment(stringBuffer, false, logs.getUId(), null);
                        // 日志
                        this.appendSegment(stringBuffer, true, logs.getLogInfo().substring(0, 20) + " ... ", logs.getLId());
                        stringBuffer.append("</tr>");
                    }
                }
            }
        }

        stringBuffer.append("</table>");

        if (isNeedSendEmail[0]) {
            boolean sendFlag = this.sendMail(stringBuffer);
            if (sendFlag) {
                log.info("\n 邮件发送成功，邮件主题：Healthy平台日志运维，日志级别 > {}", emailLogLeavel);
            } else {
                log.info("\n 邮件发送失败，失败日志\n");
            }
        }

    }

    /**
     * 发送消息
     *
     * @param htmlMessage
     * @return
     */
    private boolean sendMail(StringBuffer htmlMessage) {
        if (StringUtils.isEmpty(htmlMessage)) {
            return false;
        }
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(sendMail);
            mimeMessageHelper.setTo(sendMail);
            mimeMessageHelper.setSubject("Healthy平台日志运维，日志级别 > " + emailLogLeavel);
            mimeMessageHelper.setText(htmlMessage.toString(), true);
            javaMailSender.send(mimeMailMessage);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 构造HTML Segment
     *
     * @param stringBuffer
     * @param isAHref      是否是A标签
     * @param data         页面显示数据
     * @param dataId       A标签内容
     */
    private void appendSegment(StringBuffer stringBuffer, boolean isAHref, Object data, Long dataId) {
        stringBuffer.append("<td style=\"border:solid #add9c0; border-width:0px 1px 1px 0px;\">");
        if (isAHref) {
            stringBuffer.append("<span><a target='_blank' href='" + callBackServer + "/log/by/id/" + dataId + "'>" + data + "</span></a>");
        } else {
            stringBuffer.append("<span>" + (data == null ? "暂无" : data) + "</span>");
        }
        stringBuffer.append("</td>");
    }

    private void changePersist(List<Logs> logs) {
        for (Logs logs1 : logs) {
            logs1.setHasSendEmail(true);
            logService.updateLogsSendEmail(true, logs1.getLId());
        }
    }
}
