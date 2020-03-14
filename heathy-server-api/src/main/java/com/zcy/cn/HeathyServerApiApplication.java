package com.zcy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCircuitBreaker
@ComponentScan(basePackages = {"config"})
public class HeathyServerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeathyServerApiApplication.class, args);
    }

}
