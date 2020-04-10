package com.zcy.cn.quartz.common.bean;

import lombok.Data;

/**
 * Quartz触发器、JobDetail封装
 */
@Data
public class QuartzJobDetailTriggerBean {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

    private Class jobClass;

    private String cronExpression;

}
