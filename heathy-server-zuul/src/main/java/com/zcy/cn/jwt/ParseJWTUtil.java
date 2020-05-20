package com.zcy.cn.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 */
@Component
public class ParseJWTUtil {

    @Value("${jwt.secret:geiwodiangasfdjsikolkjikolkijswe}")
    private String jwtSecret;

    /**
     * JWT 解析Token工具类
     *
     * @param token
     * @return
     * @throws ParseException
     * @throws JOSEException
     */
    public Map<String, Object> valid(String token) throws ParseException, JOSEException {
        Map<String, Object> resultMap = new HashMap<>();
        JWSObject jwsObject = null;
        try {
            // 解析token
            jwsObject = JWSObject.parse(token);
        } catch (Exception e) {
            Map<String, Object> payload = new HashMap<>(2);
            payload.put("openId", null);
            payload.put("userId", null);
            resultMap.put("Result", 1);
            resultMap.put("Data", payload);
            return resultMap;
        }

        //获取到载荷
        Payload payload = jwsObject.getPayload();
        //建立一个解锁密匙
        JWSVerifier jwsVerifier = new MACVerifier(jwtSecret.getBytes());

        //判断token
        if (jwsObject.verify(jwsVerifier)) {
            // Token正常比标识
            resultMap.put("Result", 0);
            //载荷的数据解析成json对象
            JSONObject jsonObject = payload.toJSONObject();
            resultMap.put("data", jsonObject);

            //判断token是否过期
            if (jsonObject.containsKey("expireTime")) {
                Long expTime = Long.valueOf((String) jsonObject.get("expireTime"));
                Long nowTime = Long.valueOf(DateTime.now().toString("yyyyMMddHHmmssSSS"));
                //判断是否过期
                if (nowTime > expTime) {
                    //已经过期
                    resultMap.clear();
                    // Token已经过期
                    resultMap.put("Result", 2);
                }
            }
        } else {
            // Token 解密不通过
            resultMap.put("Result", 1);
        }
        return resultMap;
    }

}
