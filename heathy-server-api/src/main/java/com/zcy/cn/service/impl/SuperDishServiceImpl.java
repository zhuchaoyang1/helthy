package com.zcy.cn.service.impl;

import com.zcy.cn.bean.SuperDish;
import com.zcy.cn.dao.SuperDishDao;
import com.zcy.cn.service.SuperDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SuperDishServiceImpl implements SuperDishService {

    @Autowired
    private SuperDishDao superDishDao;

    @Override
    public SuperDish saveOrUpdate(SuperDish superDish) {
        return superDishDao.save(superDish);
    }

    @Override
    public List<SuperDish> saveOrUpdateAll(List<SuperDish> superDishes) {
        return superDishDao.saveAll(superDishes);
    }

    @Override
    public List<SuperDish> queryAll() {
        return superDishDao.findAll();
    }
}
