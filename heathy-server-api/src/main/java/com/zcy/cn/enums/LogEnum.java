package com.zcy.cn.enums;

/**
 * Log Leavel Enum Class.
 */
public enum LogEnum {

    ALL(0, "ALL"),
    TRACE(1, "TRACE"),
    DEBUG(2, "DEBUG"),
    INFO(3, "INFO"),
    WARN(4, "WARN"),
    ERROR(5, "ERROR");

    private Integer index;
    private String leavel;

    LogEnum(Integer index, String leavel) {
        this.index = index;
        this.leavel = leavel;
    }

    public Integer getIndex() {
        return index;
    }

    public String getLeavel() {
        return leavel;
    }

}