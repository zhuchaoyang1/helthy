package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.bean.SuperDish;
import com.zcy.cn.service.SuperDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/super/dish")
public class SuperDishController {

    @Autowired
    private SuperDishService superDishService;

    @PostMapping("/save/update")
    public ResultHttp saveOrUpdate(@RequestBody SuperDish superDish) {
        return ResultHttp.builder().code(1).result(superDishService.saveOrUpdate(superDish)).build();
    }

    @PostMapping("/save/update/batch")
    public ResultHttp saveOrUpdateBatch(@RequestBody List<SuperDish> superDishes) {
        return ResultHttp.builder().code(1).result(superDishService.saveOrUpdateAll(superDishes)).build();
    }

    @GetMapping
    public ResultHttp queryAll() {
        return ResultHttp.builder().code(1).result(superDishService.queryAll()).build();
    }
}
