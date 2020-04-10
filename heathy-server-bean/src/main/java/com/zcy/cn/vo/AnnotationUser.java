package com.zcy.cn.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配合自定义注解
 * 用于获取JWT解析出的openId
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationUser {
    private String openId;
    private Long uId;
}
