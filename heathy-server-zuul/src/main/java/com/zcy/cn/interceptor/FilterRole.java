package com.zcy.cn.interceptor;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Zuul网关
 * 拦截所有请求判断权限问题  通过Service前缀
 * 不拦截登录模块
 */
@Slf4j
@Component
public class FilterRole extends ZuulFilter {

    @Value("#{'${white.list}'.split(',')}")
    private String[] whiteApi;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 级别不能太低
        return 4;
    }

    /**
     * Ture  执行过滤器
     * False 不执行过滤器
     *
     * @return
     */
    @Override
    @HystrixCommand
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String originServerUrl = request.getRequestURI();

        PathMatcher pathMatcher = new AntPathMatcher();
        List<String> filterApi = Arrays.asList(whiteApi).stream().filter(var ->
                pathMatcher.match(var, originServerUrl)
        ).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filterApi)) {
            // 不拦截
            return false;
        } else {
            // 微服务需拦截
            return true;
        }
    }

    /**
     * ShoudFilter上的@HystrixCommand注解的优先级比Zuul Filter低
     * @HistrixCommand上的FallBack不会被执行
     * @return
     */
//    public boolean histrixCommandCallBack() {
//        log.error("错误信息");
//        return true;
//    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String orginToken = request.getHeader("Token");
        if (StringUtils.isEmpty(orginToken)) {
            // 伪造请求
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            context.getResponse().setContentType("application/json;charset=UTF-8");
            context.setResponseBody("{\"code\":0,\"result\":\"暂未登录\"}");
        }
        return null;
    }

}
