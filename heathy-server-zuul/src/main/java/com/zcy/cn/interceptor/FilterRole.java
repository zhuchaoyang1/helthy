package com.zcy.cn.interceptor;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String originServerUrl = request.getRequestURI();
        List<String> filterApi = Arrays.stream(whiteApi).filter(var -> originServerUrl.equals("/v1/api"+var)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filterApi)) {
            // 不拦截
            return false;
        } else {
            // 微服务需拦截
            return true;
        }
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String orginToken = request.getHeader("Token");
        // TODO

        return null;
    }

}
