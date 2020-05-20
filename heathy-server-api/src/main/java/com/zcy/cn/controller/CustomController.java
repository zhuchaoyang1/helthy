package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Custom;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.dto.AdminCustomChangeDTO;
import com.zcy.cn.dto.CustomDTO;
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

    @GetMapping("/admin/{bmi}")
    public ResultHttp queryAdmin(@PathVariable String bmi) {
        Integer bmiInt = Integer.parseInt(bmi);
        List<AdminCustomChangeDTO> result = customService.adminBmiWeights(bmiInt);
        return ResultHttp.builder().code(1).result(result).build();
    }

    @PutMapping
    public ResultHttp updateCWeight(@RequestBody List<AdminCustomChangeDTO> list) {
        list.forEach(var -> {
            Custom custom = new Custom();
            custom.setBmiFlag(var.getBmiFlag());
            custom.setCWeight(var.getCWeight());
            custom.setSId(var.getSId());
            customService.update(custom);
        });
        return ResultHttp.builder().code(1).result("更新成功").build();
    }
}
