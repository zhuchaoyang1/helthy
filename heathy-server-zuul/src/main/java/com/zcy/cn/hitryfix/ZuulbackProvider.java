package com.zcy.cn.hitryfix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ZUUL集成了Ribbon和Hystrix
 * 网关服务降级
 * 服务报错或找不到
 *
 * 总结：Zuul组件只针对TIME_OUT进行服务降级，因为Zuul无法判断服务返回的Error是否是前端需要的，因此不会拦截
 * 这样一来，如果FeignClient对服务进行服务降级之后，Zuul不会再对其进行服务降级了，两者的服务降级不会产生冲突
 */
@Slf4j
@Component
public class ZuulbackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        // 针对所有服务
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.error("服务模块：{} 可能存在问题，已做服务降级处理", route);
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                // 返回状态常量
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                // 返回状态码，这里为503
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() throws IOException {
                // 返回状态码对应的状态短语，这里为"Service Unavailable"
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                // 设置降级信息
                String msg = "当前服务升级中导致不可用，模块名称:" + route;
                return new ByteArrayInputStream(msg.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                // 设置降级响应头信息
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };

    }
}
