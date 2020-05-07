package com.zcy.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.PrivateLetter;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.PrivateLetterService;
import com.zcy.cn.vo.AnnotationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/private/letter")
public class PrivateLetterController {

    @Autowired
    private PrivateLetterService privateLetterService;

    @TokenModel
    @PostMapping
    public ResultHttp saveLetter(@RequestBody PrivateLetter privateLetter, HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(privateLetterService.save(privateLetter, annotationUser)).build();
    }

    @TokenModel
    @GetMapping("/count")
    public ResultHttp queryCountNoReadPrivateLetter(HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(privateLetterService.queryByNeverReadCount(annotationUser.getUId())).build();
    }

    @TokenModel
    @GetMapping("/page/{pageNo}")
    public ResultHttp queryPageNoReadPrivateLetter(@PathVariable(value = "pageNo") String pageNo, HttpServletRequest request, AnnotationUser annotationUser) {
        Pageable pageable = PageRequest.of(Integer.valueOf(pageNo), 15);
        return ResultHttp.builder().code(1).result(
                JSON.toJSONString(privateLetterService.queryByNeverRead(annotationUser.getUId(), pageable), SerializerFeature.DisableCircularReferenceDetect)).build();
    }

    @GetMapping("/by/{pLId}/id")
    public ResultHttp queryById(@PathVariable String pLId) {
        return ResultHttp.builder().code(1).result(privateLetterService.findById(Long.valueOf(pLId))).build();
    }

}
