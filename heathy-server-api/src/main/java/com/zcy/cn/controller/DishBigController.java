package com.zcy.cn.controller;

import com.zcy.cn.bean.DishBig;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.BigDishService;
import com.zcy.cn.vo.DishBigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/big/dish")
public class DishBigController {

    @Autowired
    private BigDishService bigDishService;

    @PostMapping("/insert")
    public ResultHttp saveBigDish(@RequestBody DishBigVO dishBigVO) {
        return ResultHttp.builder().code(1).result(bigDishService.save(dishBigVO)).build();
    }

    @PostMapping("/update/save/batch")
    public ResultHttp updateOrSaveBatch(@RequestBody List<DishBig> dishBigList) {
        return ResultHttp.builder().code(1).result(bigDishService.updateOrSaveBatch(dishBigList)).build();
    }

    @GetMapping("/query/all")
    public ResultHttp queryAll() {
        return ResultHttp.builder().code(1).result(bigDishService.queryAll()).build();
    }

    @PostMapping("/update")
    public ResultHttp updateByOnePresistent(@RequestBody DishBig dishBig) {
        return ResultHttp.builder().code(1).result(bigDishService.update(dishBig)).build();
    }

    @DeleteMapping("/delete")
    public ResultHttp deleyeByOnePresistent(@RequestBody DishBig dishBig) {
        bigDishService.delete(dishBig);
        return ResultHttp.builder().code(1).result("删除成功").build();
    }

}
