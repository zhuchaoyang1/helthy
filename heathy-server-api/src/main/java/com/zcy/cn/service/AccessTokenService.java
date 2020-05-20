package com.zcy.cn.service;

import java.util.Map;

public interface AccessTokenService {

    Map<String, Object> saveAccessTokenToRedis();

    Object getAccessTokenFromRedis();

}
