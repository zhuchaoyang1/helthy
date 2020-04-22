package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Custom;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.CustomService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/custom")
public class CustomController {

    @Autowired
    private CustomService customService;

    @PostMapping("/save/update")
    public ResultHttp saveOrUpdate(@RequestBody Custom custom) {
        return ResultHttp.builder().code(1).result(customService.save(custom)).build();
    }


    @PostMapping("/save/update/batch")
    public ResultHttp saveOrUpdate(@RequestBody List<Custom> customs) {
        return ResultHttp.builder().code(1).result(customService.saveOrUpdateBatch(customs)).build();
    }

    @GetMapping("/query")
    public ResultHttp queryAll() {
        return ResultHttp.builder().code(1).result(customService.queryAll()).build();
    }

    @TokenModel
    @GetMapping("/query/custom/dish")
    public ResultHttp queryByMyBodyStatus(HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(customService.buildCustomByBmi(annotationUser)).build();
    }
}
