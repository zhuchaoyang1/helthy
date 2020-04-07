package com.zcy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HeathyServerEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeathyServerEurekaApplication.class, args);
    }

}
