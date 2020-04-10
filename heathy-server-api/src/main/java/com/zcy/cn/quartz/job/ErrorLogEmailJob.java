package com.zcy.cn.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Error leavel log send email to zhuchaoyang_1@163.com
 */
public class ErrorLogEmailJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("发送邮件");
    }
}
