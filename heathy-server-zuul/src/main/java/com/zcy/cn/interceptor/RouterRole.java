//package com.zcy.cn.interceptor;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import com.zcy.cn.jwt.ParseJWTUtil;
//import com.zcy.cn.service.SendToKafka;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.PathMatcher;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * Zuul网关
// * 拦截所有请求判断权限问题  通过Service前缀
// * 不拦截登录模块
// */
//@Slf4j
//@Component
//public class RouterRole extends ZuulFilter {
//
//    @Override
//    public String filterType() {
//        return FilterConstants.ROUTE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        // 级别不能太低
//        return 5;
//    }
//
//    /**
//     * Ture  执行过滤器
//     * False 不执行过滤器
//     *
//     * @return
//     */
//    @Override
//    @HystrixCommand
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext context = RequestContext.getCurrentContext();
//        HttpServletRequest request = context.getRequest();
//        request.setAttribute("openId", "aaa");
//        request.setAttribute("uId", "aaa");
//        return null;
//    }
//
//}
