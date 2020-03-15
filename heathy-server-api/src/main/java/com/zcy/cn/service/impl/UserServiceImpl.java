package com.zcy.cn.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcy.cn.feign.wechat.server.Auth;
import com.zcy.cn.service.UserService;
import com.zcy.cn.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private Auth auth;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wechat.appid}")
    private String appId;
    @Value("${wechat.secret}")
    private String secret;

    @Override
    public Map<String, String> login(Map<String, String> map) {
        String response = auth.getOpenIdAndSessionKey(appId, secret, map.get("code"), "authorization_code");
        try {
            map = objectMapper.readValue(response, Map.class);
            redisUtils.putSessionKey(map.get("openid"), map.get("session_key"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }
}
