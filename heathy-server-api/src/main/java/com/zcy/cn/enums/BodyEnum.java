package com.zcy.cn.enums;

public enum BodyEnum {
    SHOU(0, "瘦"),
    PIANSHOU(1, "偏瘦"),
    BIAOZHUN(2, "标准"),
    PANG(3, "胖"),
    PIANPANG(4, "偏胖"),
    FEIPANG(5, "肥胖"),
    JIDUFEIPANG(6, "极度肥胖");

    private Integer index;

    private String name;

    BodyEnum(Integer index, String name) {
        this.index = index;
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
