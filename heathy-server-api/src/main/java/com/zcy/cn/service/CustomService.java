package com.zcy.cn.service;

import com.zcy.cn.bean.Custom;
import com.zcy.cn.dto.AdminCustomChangeDTO;
import com.zcy.cn.vo.AnnotationUser;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CustomService {

    Custom save(Custom custom);

    List<Custom> queryAll();

    List<Custom> saveOrUpdateBatch(List<Custom> customs);

    Map<String, Object> buildCustomByBmi(AnnotationUser annotationUser);

    List<AdminCustomChangeDTO> adminBmiWeights(Integer bmiFlag);

    void update(Custom custom);


}
