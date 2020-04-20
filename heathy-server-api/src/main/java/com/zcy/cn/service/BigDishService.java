package com.zcy.cn.service;

import com.zcy.cn.bean.DishBig;
import com.zcy.cn.vo.DishBigVO;

import java.util.List;

public interface BigDishService {

    DishBigVO save(DishBigVO dishBigVO);

    List<DishBig> queryAll();

    DishBig update(DishBig dishBig);

    void delete(DishBig dishBig);

}
