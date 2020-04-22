package com.zcy.cn.dto;

import lombok.Data;

/**
 * 食品推荐DTO
 */
@Data
public class CustomDTO {

    /**
     * SuperDish
     */
    public Long sId;       // SuperDish主键 用于关联Custom
    public String name;    // SuperDish大类名称

    /**
     * BigDish
     */
    public Long dbId;      // DisBig主键 勾选任务是否完成是基于该字段完成度的百分比
    public String smallName;
    public String icon;
    public String instructions;

    /**
     * Dish
     */
    public String dishBigName;    // Dish表name需要指定别名为dishName

    /**
     * Custom
     */
    public Integer bmiFlag;
    public Double cWeight;

    public CustomDTO(String name, String smallName, String instructions,
                     String dishBigName, Long dbId, String icon, Double cWeight, Integer bmiFlag) {
        this.name = name;
        this.smallName = smallName;
        this.instructions = instructions;
        this.dishBigName = dishBigName;
        this.dbId = dbId;
        this.icon = icon;
        this.cWeight = cWeight;
        this.bmiFlag = bmiFlag;
    }

}
