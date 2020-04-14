package com.zcy.cn.quartz.start;

import com.zcy.cn.quartz.common.bean.Management;
import com.zcy.cn.quartz.common.bean.QuartzJobDetailTriggerBean;
import com.zcy.cn.quartz.constant.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JobStarter {

    @Autowired
    private Management management;

    @Value("${email.corn}")
    private String emailCornExpresion;

    @PostConstruct
    public void buildJobStart() {
        log.info("\n日志告警定时任务已开始长跑");
        try {
            management.addJobAndRun(this.buildEmailJob());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 邮件任务构建
    private QuartzJobDetailTriggerBean buildEmailJob() {
        QuartzJobDetailTriggerBean quartzJobDetailTriggerBean = new QuartzJobDetailTriggerBean();
        quartzJobDetailTriggerBean.setJobName(EmailConstant._EMAILJOBNAME);
        quartzJobDetailTriggerBean.setJobGroupName(EmailConstant._EMAILJOBGROUPNAME);
        quartzJobDetailTriggerBean.setTriggerName(EmailConstant._EMAILTRIGGERNAME);
        quartzJobDetailTriggerBean.setTriggerGroupName(EmailConstant._EMAILTRIGGERGROUPNAME);
        quartzJobDetailTriggerBean.setJobClass(EmailConstant._EMAILJOBCLASS);
        quartzJobDetailTriggerBean.setCronExpression(emailCornExpresion);
        return quartzJobDetailTriggerBean;
    }


}
