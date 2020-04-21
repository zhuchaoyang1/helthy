package com.zcy.cn.enums;

public enum BmiEnum {

    CHINASTAND(0, "中国标准", new Double[]{18.5, 24.0, 28.0, 30.0, 40.0}),
    ASIATAND(1, "亚洲标准", new Double[]{18.5, 23.0, 25.0, 30.0, 40.0}),
    WHOSTAND(2, "世卫标准", new Double[]{18.5, 25.0, 30.0, 35.0, 40.0});

    private Integer standardIndex;
    private String standardName;
    private Double[] doubles;

    BmiEnum(Integer standardIndex, String standardName, Double[] doubles) {
        this.standardIndex = standardIndex;
        this.standardName = standardName;
        this.doubles = doubles;
    }

    public Integer getStandardIndex() {
        return standardIndex;
    }

    public String getStandardName() {
        return standardName;
    }

    public Double[] getDoubles() {
        return doubles;
    }
}
