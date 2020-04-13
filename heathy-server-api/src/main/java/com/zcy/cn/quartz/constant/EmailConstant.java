package com.zcy.cn.quartz.constant;

import com.zcy.cn.quartz.job.ErrorLogEmailJob;

/**
 * 邮件告警相关Quartz Job常量
 */
public class EmailConstant {

    public static final String _EMAILJOBNAME = "email_job";
    public static final String _EMAILJOBGROUPNAME = "email_group_job";
    public static final String _EMAILTRIGGERNAME = "email_trigger";
    public static final String _EMAILTRIGGERGROUPNAME = "email_trigger_name";
    public static final Class _EMAILJOBCLASS = ErrorLogEmailJob.class;


}
