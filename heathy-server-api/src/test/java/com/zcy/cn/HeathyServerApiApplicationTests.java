package com.zcy.cn;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.dao.BodyArgsDao;
import com.zcy.cn.quartz.common.bean.Management;
import com.zcy.cn.quartz.common.bean.QuartzJobDetailTriggerBean;
import com.zcy.cn.quartz.job.ErrorLogEmailJob;
import com.zcy.cn.service.AccessTokenService;
import com.zcy.cn.service.LogService;
import com.zcy.cn.service.VisitFromWechat;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Slf4j
@SpringBootTest
public class HeathyServerApiApplicationTests {

    @Autowired
    private Management management;

    @Autowired
    private LogService logService;

    @Autowired
    private BodyArgsDao bodyArgsDao;

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private VisitFromWechat visitFromWechat;


    @Test
    public void linkedListTest() {
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < 5; i++) {
            linkedList.add(i);
        }
        log.info(linkedList.toString());
    }

    // Quartz测试
    @Test
    public void testQuartz() throws Exception {
        QuartzJobDetailTriggerBean quartzJobDetailTriggerBean = new QuartzJobDetailTriggerBean();
        quartzJobDetailTriggerBean.setJobName("ceshi");
        quartzJobDetailTriggerBean.setJobGroupName("ceshig");
        quartzJobDetailTriggerBean.setTriggerName("trigger");
        quartzJobDetailTriggerBean.setTriggerGroupName("triggerg");
        quartzJobDetailTriggerBean.setJobClass(ErrorLogEmailJob.class);
        quartzJobDetailTriggerBean.setCronExpression("0/5 * * * * ?");
        management.addJobAndRun(quartzJobDetailTriggerBean);
        Thread.sleep(500000);
    }

    // 发送运维邮箱前置查询
    @Test
    public void testErrorLogs() {
        Page<Logs> logs = logService.findAllByLeavelAndHasSendEmail("error", false);
        log.info(logs.getContent().toString());
    }

    // 获取 ACCESSTOKEN
    @Test
    public void testGetAccessToken() {
        Object result = accessTokenService.saveAccessTokenToRedis();
        log.info(result.toString());
    }

    @Test
    public void visitTread() {
        Map<String, String> payLoad = new HashMap<>();
        payLoad.put("begin_date", "20200511");
        payLoad.put("end_date", "20200517");
        String accessToken = accessTokenService.getAccessTokenFromRedis().toString();
        Object reuslt = visitFromWechat.getVisitTread(accessToken, payLoad);
    }

    @Test
    public void visitDaliyTread() {
        Map<String, String> payLoad = new HashMap<>();
        payLoad.put("begin_date", "20200518");
        payLoad.put("end_date", "20200518");
        String accessToken = accessTokenService.getAccessTokenFromRedis().toString();
        Object reuslt = visitFromWechat.getDaliyTread(accessToken, payLoad);
    }



}
