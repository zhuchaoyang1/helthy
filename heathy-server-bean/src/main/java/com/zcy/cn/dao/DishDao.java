package com.zcy.cn.dao;

import com.zcy.cn.bean.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishDao extends JpaRepository<Dish, Long> {


}
