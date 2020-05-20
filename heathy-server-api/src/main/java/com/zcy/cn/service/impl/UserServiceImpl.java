package com.zcy.cn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Users;
import com.zcy.cn.dao.UserDao;
import com.zcy.cn.feign.wechat.server.Auth;
import com.zcy.cn.refresh.AdminRefresh;
import com.zcy.cn.service.UserService;
import com.zcy.cn.util.RedisUtils;
import com.zcy.cn.vo.UsersVO;
import jwt.GenJWTUTil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Autowired
    private UserDao userDao;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    @Autowired
    private GenJWTUTil genJWTUTil;

    // Admin的配置给出默认配置为了避免因为网络延迟问题导致GitHubConfig获取失败
//    @Value("${admin.uName:admin}")
//    private String adminUName;
//
//    @Value("${admin.uPwd:zhuchaoyang}")
//    private String adminUPwd;

    /**
     * 从Github中获取动态配置
     */
    @Autowired
    private AdminRefresh adminRefresh;

    /**
     * 每一次小程序发起的登录都会重新刷新Redis Session_key
     * 并返回该用户数据库中的记录
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> login(Map<String, String> map) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> jwtMap = new HashMap<>();
        String response = auth.getOpenIdAndSessionKey(appId, secret, map.get("code"), "authorization_code");
        try {
            map = objectMapper.readValue(response, Map.class);
            // 根据OpenID查询数据库
            Users usersFromData = userDao.findByOpenId(String.valueOf(map.get("openid")));
            if (usersFromData != null) {
                // 将UId、OpenId放入
                jwtMap.put("uId", usersFromData.getUId());

                result.put("users", usersFromData.createUserVO());
                // 将Session_key添加至缓存
                redisUtils.putSessionKey(map.get("openid"), map.get("session_key"));
            }
            jwtMap.put("openId", map.get("openid"));
            result.put("jwt", genJWTUTil.creatToken(jwtMap));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    @TokenModel
    public UsersVO reg(Users users) {
        return userDao.save(users).createUserVO();
    }


    @Override
    public Users findById(Long id) {
        return userDao.findById(id).orElse(new Users());
    }

    @Override
    public Map<String, Object> adminLogin(JSONObject jsonObject) {
        boolean flag = false;
        Map<String, Object> result = new HashMap<>();
        if (jsonObject.containsKey("uName") && jsonObject.containsKey("uPwd")) {
            Object uName = jsonObject.get("uName");
            Object uPwd = jsonObject.get("uPwd");
            if (adminRefresh.getUName().equals(String.valueOf(uName)) && adminRefresh.getUPwd().equals(String.valueOf(uPwd))) {
                flag = true;
            }
        }

        if (flag) {
            Map<String, Object> jwtMap = new HashMap<>();
            jwtMap.put("symbol", "pc");              // 代表是PC标志
            jwtMap.put("openId", adminRefresh.getUName());        // 为了和小程序字段统一
            jwtMap.put("uId", adminRefresh.getUPwd());            // 为了和小程序字段统一
            try {
                result.put("jwt", genJWTUTil.creatToken(jwtMap));
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        }
        result.put("code", flag);
        return result;
    }

}
