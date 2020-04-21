package com.zcy.cn.service;

import com.zcy.cn.bean.Custom;

import java.util.List;

public interface CustomService {

    Custom save(Custom custom);

    List<Custom> queryAll();

    List<Custom> saveOrUpdateBatch(List<Custom> customs);

}
