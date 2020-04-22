package com.zcy.cn.service;

import com.zcy.cn.bean.Custom;
import com.zcy.cn.vo.AnnotationUser;

import java.util.List;
import java.util.Map;

public interface CustomService {

    Custom save(Custom custom);

    List<Custom> queryAll();

    List<Custom> saveOrUpdateBatch(List<Custom> customs);

    List<Map<String, Object>> buildCustomByBmi(AnnotationUser annotationUser);
}
