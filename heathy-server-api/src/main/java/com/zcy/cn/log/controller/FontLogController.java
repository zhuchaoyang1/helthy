package com.zcy.cn.log.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Logs;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.quartz.job.ErrorLogEmailJob;
import com.zcy.cn.service.LogService;
import com.zcy.cn.vo.AnnotationUser;
import io.netty.handler.logging.LogLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 收集来自小程序端、网页的Error日志
 */
@RestController
@RequestMapping("/log")
@Api("平台日志运维")
public class FontLogController {

    @Autowired
    private LogService logService;


    @ApiOperation(value = "添加日志", notes = "添加日志")
    @TokenModel
    @PostMapping("/save")
    public ResultHttp saveLog(@RequestBody Logs logs, HttpServletRequest request, AnnotationUser annotationUser) {
        logs.setUId(annotationUser.getUId());
        return ResultHttp.builder().code(logService.save(logs) ? 0 : 1).result(null).build();
    }

    @GetMapping("/by/id/{id}")
    public String query(@PathVariable Long id) {
        Optional<Logs> op = logService.findById(id);
        return op.orElseGet(() -> new Logs()).getLogInfo();
    }


}
