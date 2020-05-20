package com.zcy.cn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 微信OpenID --> sessionKey缓存
     * 由于session_key由微信对开发者完全透明，因此Redis维护的过期时间需设置较长
     *
     * @param openId
     * @param sessionKey
     */
    public void putSessionKey(String openId, String sessionKey) {
        // redisTemplate.opsForHash().put("session_key", openId, sessionKey);
        redisTemplate.opsForValue().set(openId, sessionKey);
        redisTemplate.expire("session_key", 1, TimeUnit.DAYS);
    }

    /**
     * 存放小程序唯一凭证 ACCESS_TOKEN
     *
     * @param accessToken
     * @param expireTime
     */
    public void putAccessToken(String accessToken, long expireTime) {
        redisTemplate.delete("ACCESSTOKEN");
        redisTemplate.opsForValue().append("ACCESSTOKEN", accessToken);
        // expireTime = 7200
        expireTime = expireTime / 3600;
        redisTemplate.expire("ACCESSTOKEN", expireTime, TimeUnit.HOURS);
    }

    /**
     * 获取小程序ACCESSTOKEN
     *
     * @return
     */
    public Object getAccessToken() {
        return redisTemplate.opsForValue().get("ACCESSTOKEN");
    }

}
