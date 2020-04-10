package com.zcy.cn.quartz.common.bean;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Management {

    @Autowired
    private Scheduler scheduler;

    public void addJobAndRun(QuartzJobDetailTriggerBean quartzJobDetailTriggerBean) throws Exception {
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(quartzJobDetailTriggerBean.getJobClass()).
                withIdentity(quartzJobDetailTriggerBean.getJobName(),quartzJobDetailTriggerBean.getJobGroupName())
                .build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJobDetailTriggerBean.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().
                withIdentity(quartzJobDetailTriggerBean.getTriggerName(), quartzJobDetailTriggerBean.getTriggerGroupName())
                .withSchedule(scheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new Exception("Error");
        }
    }


}
