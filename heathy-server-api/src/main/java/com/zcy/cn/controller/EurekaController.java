package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/eureka")
public class EurekaController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/get/application")
    public ResultHttp getServicesList() {
        List<ServiceInstance> servicesList = new ArrayList<>();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices();
        for (String serviceName : serviceNames) {   // 可能一个appname对应多个服务做负载均衡
            //获取服务中的实例列表
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            servicesList.addAll(serviceInstances);
        }
        return ResultHttp.builder().code(1).result(servicesList).build();
    }

}
