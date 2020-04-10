package com.zcy.cn.enums;

/**
 * Log Leavel Enum Class.
 */
public enum LogEnum {

    ALL("ALL"),
    TRACE("TRACE"),
    DEBUG("DEBUG"),
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR");

    private String leavel;

    LogEnum(String leavel) {
        this.leavel = leavel;
    }

    public String getLeavel() {
        return leavel;
    }

}