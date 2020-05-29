package com.zcy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.util.concurrent.ThreadPoolExecutor;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class HeathyServerZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeathyServerZuulApplication.class, args);
    }

}
