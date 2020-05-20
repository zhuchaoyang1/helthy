package com.zcy.cn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCustomChangeDTO {

    /**
     * SuperDish
     */
    public Long sId;       // SuperDish主键 用于关联Custom
    public String name;    // SuperDish大类名称

    /**
     * Custom
     */
    public Double cWeight;
    public Integer bmiFlag;

}
