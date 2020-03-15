package com.zcy.cn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 微信OpenID
     * @param openId
     * @param sessionKey
     */
    public void putSessionKey(String openId, String sessionKey) {
        redisTemplate.opsForHash().put("session_key", openId, sessionKey);
    }

}
