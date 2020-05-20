package com.zcy.cn.service.impl;

import com.alibaba.fastjson.JSON;
import com.zcy.cn.feign.wechat.server.AccessToken;
import com.zcy.cn.service.AccessTokenService;
import com.zcy.cn.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private AccessToken accessToken;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> saveAccessTokenToRedis() {
        Map<String, Object> map = new HashMap<>();

        String resultFromWechatServer = accessToken.getAccessToken(appId, secret);
        Map<String, Object> accessResult = JSON.parseObject(resultFromWechatServer, Map.class);
        if (accessResult.containsKey("errcode") && accessResult.containsKey("errmsg")) {
            map.put("flag", false);
            map.put("result", accessResult.get("errmsg"));
            return map;
        }

        redisUtils.putAccessToken(accessResult.get("access_token").toString(), Long.valueOf(accessResult.get("expires_in").toString()));
        map.put("flag", true);
        map.put("result", "保存成功");
        return map;
    }

    /**
     * response == null 说明ACCESSTOKEN在Redis中已过期  或者没有存储过
     * 则进行存储  因此外界不需要显示的调用saveAccess方法，只需要无感知的调用
     *
     * @return
     */
    @Override
    public Object getAccessTokenFromRedis() {
        Object accessToken = redisUtils.getAccessToken();
        if (accessToken == null) {
            this.saveAccessTokenToRedis();
        }
        return redisUtils.getAccessToken();
    }


}
