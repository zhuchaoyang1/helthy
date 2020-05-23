package com.zcy.cn.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zcy.cn.contanst.Contanst;
import com.zcy.cn.jwt.ParseJWTUtil;
import com.zcy.cn.service.SendToKafka;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Zuul网关
 * 拦截所有请求判断权限问题  通过Service前缀
 * 不拦截登录模块
 */
@Slf4j
@Component
public class FilterRole extends ZuulFilter {

    @Autowired
    private SendToKafka sendToKafka;

    @Value("#{'${white.list}'.split(',')}")
    private String[] whiteApi;

    @Autowired
    private ParseJWTUtil parseJWTUtil;

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
                // 在白名单每一项添加/**避免Zuul服务名前缀替换后 每次都需要更改白名单相关配置
                pathMatcher.match("/**" + var, originServerUrl)
        ).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(filterApi)) {
            // 不拦截
            request.setAttribute("openId", null);
            request.setAttribute("uId", null);
            return false;
        } else {
            // 微服务需拦截
            return true;
        }
    }

    /**
     * ShoudFilter上的@HystrixCommand注解的优先级比Zuul Filter低
     *
     * @return
     * @HistrixCommand上的FallBack不会被执行
     */
//    public boolean histrixCommandCallBack() {
//        log.error("错误信息");
//        return true;
//    }

//    简单网关认证方案  （不对口令进行验证，只验证其有无）
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext context = RequestContext.getCurrentContext();
//        HttpServletRequest request = context.getRequest();
//        String orginToken = request.getHeader("Token");
//        if (StringUtils.isEmpty(orginToken)) {
//            // 构造请求结果
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(401);
//            context.getResponse().setContentType("application/json;charset=UTF-8");
//            context.setResponseBody("{\"code\":0,\"result\":\"暂未登录\"}");
//        }
//        return null;
//    }
    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            // Options预请求 直接放行
            request.setAttribute("openId", null);
            request.setAttribute("uId", null);
            return null;
        }

        String token = request.getHeader("Token");

        // 令牌为空
        if (StringUtils.isEmpty(token)) {
            this.noLogin(context);
            return null;
        }

        // POSTMAN埋点
        if (!StringUtils.isEmpty(token) && token.equals("POSTMAN")) {
            request.setAttribute("openId", null);
            request.setAttribute("uId", null);
            return null;
        }

        try {
            Map<String, Object> resultMap = parseJWTUtil.valid(token);
            if (resultMap.get("Result").equals(0)) {
                Map<String, String> playload = JSONObject.parseObject(resultMap.get("data").toString(), new TypeReference<Map<String, String>>() {
                });
                // 获取openId以及uID 并放入注解中用于Controller取值
                Object openId = playload.get("openId");
                Object uId = playload.get("uId");
                request.setAttribute("openId", openId);
                request.setAttribute("uId", uId);

                // 流量记录Kafka
                playload.put("RequestURL", request.getRequestURI());
                sendToKafka.sendToTopic(Contanst._FLOWTOPIC, JSON.toJSONString(playload));
            } else {
                this.noLogin(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.noLogin(context);
        }

        return null;
    }

    private void noLogin(RequestContext context) {
        context.setSendZuulResponse(false);
        context.setResponseStatusCode(401);
        context.getResponse().setContentType("application/json;charset=UTF-8");
        context.setResponseBody("{\"code\":0,\"result\":\"暂未登录\"}");
    }
}
