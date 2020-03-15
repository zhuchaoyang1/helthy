package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 小程序用户认证相关接口
 * 依赖于微信服务  heathy-server-redis组件
 */
@RestController
@RequestMapping("/user")
@Api("微信小程序登录接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value="用户登录", notes="用户登录接口")
    @PostMapping("/login")
    public ResultHttp login(@RequestBody Map<String, String> map) {
        map = userService.login(map);
        if (map.containsKey("session_key")) {
            map.remove("session_key");
        }
        return ResultHttp.builder().code(map.containsKey("errcode") ? -1 : 0).result(map).build();
    }


}
