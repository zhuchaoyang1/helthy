package com.zcy.cn;

import com.zcy.cn.quartz.common.bean.Management;
import com.zcy.cn.quartz.common.bean.QuartzJobDetailTriggerBean;
import com.zcy.cn.quartz.job.ErrorLogEmailJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeathyServerApiApplicationTests {

    @Autowired
    private Management management;

    @Test
    public void contextLoads() throws Exception {
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

}
