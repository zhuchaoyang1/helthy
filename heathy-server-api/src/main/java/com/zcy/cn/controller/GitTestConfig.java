package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.refresh.BeanRefresh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该类用于测试Github Config更新是否有效
 * 测试使用POSTMAN测试  且 Header：{'Token': 'POSTMAN'}
 * 前提： 测试之前，若Github中配置了hook钩子 则不需要调用Refresh
 * 否则需要调用Refresh API：http://host:port/actuator/refresh
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class GitTestConfig {

    @Autowired
    private BeanRefresh beanRefresh;

    @GetMapping
    public ResultHttp getConfig() {
        return ResultHttp.builder().code(1).result(beanRefresh).build();
    }

}
