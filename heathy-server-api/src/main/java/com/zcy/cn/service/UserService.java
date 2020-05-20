package com.zcy.cn.service;


import com.alibaba.fastjson.JSONObject;
import com.zcy.cn.bean.Users;
import com.zcy.cn.vo.UsersVO;

import java.util.Map;

public interface UserService {

    Map<String, Object> login(Map<String, String> map);

    UsersVO reg(Users users);

    Users findById(Long id);

    Map<String,Object> adminLogin(JSONObject jsonObject);

}
