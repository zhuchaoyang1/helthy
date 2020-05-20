package com.zcy.cn.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nimbusds.jose.JOSEException;
import jwt.ParseJWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class LoginFilter implements Filter {

    @Value("#{'${white.list}'.split(',')}")
    private String[] whiteApi;

    @Autowired
    private ParseJWTUtil parseJWTUtil;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

//    Api核心服务内嵌Token鉴权 暂且关闭  鉴权由Gateway统一鉴权
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        boolean flag = true;
//
//        String requestUrl = request.getRequestURI();
//
//        PathMatcher pathMatcher = new AntPathMatcher();
//        List<String> contrastList = Arrays.asList(whiteApi).stream().filter(var ->
//                pathMatcher.match(var, requestUrl)
//        ).collect(Collectors.toList());
//
//        if (request.getMethod().equals(RequestMethod.OPTIONS.name()) || !CollectionUtils.isEmpty(contrastList)) {
//            // Options预请求 && 白名单
//            flag = true;
//            servletRequest.setAttribute("openId", null);
//            servletRequest.setAttribute("uId", null);
//        } else {
//            String token = request.getHeader("Token");
//            // POSTMAN埋点
//            if (!StringUtils.isEmpty(token) && token.equals("POSTMAN")) {
//                flag = true;
//            }
//            if (!StringUtils.isEmpty(token) && !token.equals("POSTMAN")) {
//                try {
//                    Map<String, Object> resultMap = parseJWTUtil.valid(token);
//                    if (resultMap.get("Result").equals(0)) {
//                        flag = true;
//                        Map<String, String> playload = JSONObject.parseObject(resultMap.get("data").toString(), new TypeReference<Map<String, String>>() {
//                        });
//                        // 获取openId以及uID 并放入注解中用于Controller取值
//                        Object openId = playload.get("openId");
//                        Object uId = playload.get("uId");
//                        servletRequest.setAttribute("openId", openId);
//                        servletRequest.setAttribute("uId", uId);
//                    } else {
//                        flag = false;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    flag = false;
//                }
//            }
//            if (StringUtils.isEmpty(token)) {
//                flag = false;
//            }
//        }
//
//        if (flag) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            response.sendRedirect("/user/no/login");
//        }
//
//    }

    /**
     * 配合Zuul使用
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUrl = request.getRequestURI();

        PathMatcher pathMatcher = new AntPathMatcher();
        List<String> contrastList = Arrays.asList(whiteApi).stream().filter(var ->
                pathMatcher.match(var, requestUrl)
        ).collect(Collectors.toList());

        String token = request.getHeader("Token");

        boolean flag = true;

        // TODO 后期可以考虑该部分有Gateway去构造 （难题）
        if (!StringUtils.isEmpty(token)) {
            // 构造Request
            Map<String, Object> resultMap = null;
            try {
                resultMap = parseJWTUtil.valid(token);
                if (resultMap.get("Result").equals(0)) {
                    Map<String, String> playload = JSONObject.parseObject(resultMap.get("data").toString(), new TypeReference<Map<String, String>>() {
                    });
                    // 获取openId以及uID 并放入注解中用于Controller取值
                    Object openId = playload.get("openId");
                    Object uId = playload.get("uId");
                    servletRequest.setAttribute("openId", openId);
                    servletRequest.setAttribute("uId", uId);
                } else {
                    flag = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }

        // 此处只做简单处理  其实Gateway已经将该中情况屏蔽掉
        if ((!StringUtils.isEmpty(request.getHeader("Token")) || contrastList.size() > 0) || (!StringUtils.isEmpty(token) && flag)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/user/no/login");
        }
    }

    public void destroy() {

    }
}
