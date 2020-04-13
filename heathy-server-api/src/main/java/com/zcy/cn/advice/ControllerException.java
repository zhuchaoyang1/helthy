package com.zcy.cn.advice;

import com.alibaba.fastjson.JSONObject;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Logs;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.LogService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ControllerException {

    @Autowired
    private LogService logService;

    @ExceptionHandler(Exception.class)
    public void handler(Exception e, HandlerMethod method, HttpServletResponse resp, HttpServletRequest request) throws IOException {
        log.error("\n方法名：{}, \n异常记录：{}", method.getMethod().getName(), e.toString());
        Logs logs = Logs.builder().hasSendEmail(false).leavel("ERROR").lId(null)
                .platforms(request.getRequestURI().contains("admin") ? "WEB" : "WECHAT").logInfo("方法名：" + method.getMethod().getName() + "异常信息：" + e.toString()).build();
        logService.save(logs);
        resp.setContentType("application/json;charset=UTF-8");
        ResultHttp resultHttp = ResultHttp.builder().code(0).result("网路错误，请稍等重试").build();
        resp.getWriter().write(JSONObject.toJSONString(resultHttp));
    }

}
