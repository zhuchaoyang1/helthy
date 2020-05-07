package com.zcy.cn;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.dao.BodyArgsDao;
import com.zcy.cn.quartz.common.bean.Management;
import com.zcy.cn.quartz.common.bean.QuartzJobDetailTriggerBean;
import com.zcy.cn.quartz.job.ErrorLogEmailJob;
import com.zcy.cn.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@SpringBootTest
public class HeathyServerApiApplicationTests {

    @Autowired
    private Management management;

    @Autowired
    private LogService logService;

    @Autowired
    private BodyArgsDao bodyArgsDao;

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

//    public static void main(String[] args) {
//        LinkedList linkedList = new LinkedList();
//        for (int i = 0; i < 5; i++) {
//            linkedList.add(i+", ");
//        }
//        log.info(linkedList.getFirst().toString());
//        log.info(linkedList.getLast().toString());
//        log.info(linkedList.getLast().toString().substring(0,linkedList.getLast().toString().length() - 2));
//        linkedList.removeLast();
//        linkedList.add(linkedList.getLast().toString().substring(0,linkedList.getLast().toString().length() - 2));
//        log.info(linkedList.toString());
//    }

    public static void main(String[] args) throws Exception {

    }


}
