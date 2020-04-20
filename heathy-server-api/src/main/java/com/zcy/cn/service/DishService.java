package com.zcy.cn.service;

import com.zcy.cn.bean.Dish;
import com.zcy.cn.bean.DishBig;
import com.zcy.cn.vo.DishBigVO;
import com.zcy.cn.vo.DishVO;

import java.util.List;

public interface DishService {

    DishVO save(DishVO dishVO);

    List<Dish> queryAll();

    Dish update(Dish dish);

    void delete(Dish dish);

}
