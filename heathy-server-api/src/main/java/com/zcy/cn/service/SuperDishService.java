package com.zcy.cn.service;

import com.zcy.cn.bean.SuperDish;

import java.util.List;

public interface SuperDishService {

    SuperDish saveOrUpdate(SuperDish superDish);

    List<SuperDish> saveOrUpdateAll(List<SuperDish> superDishes);

    List<SuperDish> queryAll();
}
