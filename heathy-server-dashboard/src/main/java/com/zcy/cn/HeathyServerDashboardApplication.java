package com.zcy.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HeathyServerDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeathyServerDashboardApplication.class, args);
    }


}
