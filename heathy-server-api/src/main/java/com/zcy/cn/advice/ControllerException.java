package com.zcy.cn.advice;

import com.alibaba.fastjson.JSONObject;
import com.zcy.cn.bean.ResultHttp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(Exception.class)
    public void handler(Exception e, HandlerMethod method, HttpServletResponse resp) throws
            IOException {
        log.error("\n方法名：{}, \n异常记录：{}", method.getMethod().getName(), e.toString());
        resp.setContentType("application/json;charset=UTF-8");
        ResultHttp resultHttp = ResultHttp.builder().code(0).result("网路错误，请稍等重试").build();
        resp.getWriter().write(JSONObject.toJSONString(resultHttp));
    }

}
