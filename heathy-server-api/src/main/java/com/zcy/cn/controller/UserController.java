package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.bean.Users;
import com.zcy.cn.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
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

    @ApiOperation(value = "用户登录", notes = "用户登录接口")
    @PostMapping("/login")
    public ResultHttp login(@RequestBody Map<String, String> map) {
        return ResultHttp.builder().code(1).result(userService.login(map)).build();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @PostMapping("/reg")
    public ResultHttp register(@RequestBody Users userInfo) {
        if (!StringUtils.isEmpty(userInfo.getOpenId())) {
            return ResultHttp.builder().code(1).result(userService.reg(userInfo)).build();
        }
        return ResultHttp.builder().code(0).result("数据库中已存在记录").build();
    }


}
