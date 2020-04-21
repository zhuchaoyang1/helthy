package com.zcy.cn.controller;

import com.zcy.cn.bean.Dish;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.DishService;
import com.zcy.cn.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping("/insert")
    public ResultHttp saveDish(@RequestBody DishVO dishVO) {
        return ResultHttp.builder().code(1).result(dishService.save(dishVO)).build();
    }

    @PostMapping("/update/save/batch")
    public ResultHttp saveBatchDish(@RequestBody List<Dish> dishes) {
        return ResultHttp.builder().code(1).result(dishService.saveBatchDish(dishes)).build();
    }

    @GetMapping("/query/all")
    public ResultHttp queryAll() {
        return ResultHttp.builder().code(1).result(dishService.queryAll()).build();
    }

    @PostMapping("/update")
    public ResultHttp updateByOnePresistent(@RequestBody Dish dish) {
        return ResultHttp.builder().code(1).result(dishService.update(dish)).build();
    }

    @DeleteMapping("/delete")
    public ResultHttp deleyeByOnePresistent(@RequestBody Dish dish) {
        dishService.delete(dish);
        return ResultHttp.builder().code(1).result("删除成功").build();
    }

}
