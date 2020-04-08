package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.bean.Users;
import com.zcy.cn.service.UserService;
import com.zcy.cn.vo.AnnotationUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @TokenModel
    public ResultHttp register(@RequestBody Users userInfo, HttpServletRequest request, AnnotationUser annotationUser) {
        String openId = (String) request.getAttribute("openId");
        if (!StringUtils.isEmpty(openId)) {
            userInfo.setOpenId(openId);
            return ResultHttp.builder().code(1).result(userService.reg(userInfo)).build();
        }
        return ResultHttp.builder().code(0).result("数据库中已存在记录").build();
    }


    @ResponseBody
    @GetMapping("/no/login")
    public ResultHttp logout() {
        return ResultHttp.builder().code(0).result("暂未登录").build();
    }


}
