package com.zcy.cn.quartz.common.bean;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Management {

    @Autowired
    private Scheduler scheduler;

    public void addJobAndRun(QuartzJobDetailTriggerBean quartzJobDetailTriggerBean) throws Exception {
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(quartzJobDetailTriggerBean.getJobClass()).
                withIdentity(quartzJobDetailTriggerBean.getJobName(), quartzJobDetailTriggerBean.getJobGroupName())
                .build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJobDetailTriggerBean.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(quartzJobDetailTriggerBean.getTriggerName(), quartzJobDetailTriggerBean.getTriggerGroupName())
                .withSchedule(scheduleBuilder)
                .build();

        try {
            if (!scheduler.checkExists(JobKey.jobKey(quartzJobDetailTriggerBean.getJobName(), quartzJobDetailTriggerBean.getJobGroupName()))) {
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                log.info("\n任务已存在");
            }
        } catch (SchedulerException e) {
            throw new Exception("Error");
        }
    }


}
