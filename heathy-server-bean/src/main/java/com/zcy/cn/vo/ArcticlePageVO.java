package com.zcy.cn.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArcticlePageVO {

    private Long tagId;

    private Integer pageNo;

    private Integer pageSize;       // 备用

    private Integer frontSize;

}
