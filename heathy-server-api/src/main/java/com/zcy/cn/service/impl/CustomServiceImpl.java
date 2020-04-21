package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Custom;
import com.zcy.cn.dao.CustomDao;
import com.zcy.cn.service.CustomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    @Autowired
    private CustomDao customDao;

    @Override
    public Custom save(Custom custom) {
        return customDao.save(custom);
    }

    @Override
    public List<Custom> queryAll() {
        return customDao.findAll();
    }

    @Override
    public List<Custom> saveOrUpdateBatch(List<Custom> customs) {
        return customDao.saveAll(customs);
    }
}
