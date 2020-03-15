package com.zcy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"config"})
public class HeathyServerRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeathyServerRedisApplication.class, args);
    }

}
