//package com.zcy.cn.interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//public class CORSInterceptor extends HandlerInterceptorAdapter {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Accept,Content-Type,Token");
//        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        return true;
//    }
//}
