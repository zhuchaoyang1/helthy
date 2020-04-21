package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Dish;
import com.zcy.cn.dao.DishDao;
import com.zcy.cn.service.DishService;
import com.zcy.cn.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;

    @Override
    public DishVO save(DishVO dishVO) {
        Dish dish = dishVO.cretaeDish();
        dish = dishDao.save(dish);
        BeanUtils.copyProperties(dish, dishVO, "dId");
        return dishVO;
    }

    @Override
    public List<Dish> queryAll() {
        return dishDao.findAll();
    }

    @Override
    public Dish update(Dish dish) {
        return dishDao.save(dish);
    }

    @Override
    public void delete(Dish dish) {
        dishDao.delete(dish);
    }

    @Override
    public List<Dish> saveBatchDish(List<Dish> dishes) {
        return dishDao.saveAll(dishes);
    }
}
