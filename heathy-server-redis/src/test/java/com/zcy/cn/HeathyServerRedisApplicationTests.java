package com.zcy.cn;

import config.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class HeathyServerRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @org.junit.jupiter.api.Test
    void contextLoads() {
        Beans beans1 = Beans.builder().openId(1).session_key("zcy").build();
        redisTemplate.opsForHash().put("session_key", String.valueOf(beans1.getOpenId()), beans1);
        Object obj = redisTemplate.opsForHash().get("session_key", String.valueOf(beans1.getOpenId()));
        Assert.isTrue(beans1.equals(obj), HttpStatus.REDIS_NO_VALUE_BY_KEY.getValue());
    }

}

/**
 * From Wechat
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Beans {
    private Integer openId;
    private String session_key;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj instanceof Beans) {
            Beans other = (Beans) obj;
            //需要比较的字段相等，则这两个对象相等
            if (this.openId == other.getOpenId() && this.session_key.equals(other.getSession_key())) {
                return true;
            }
        }

        return false;
    }
}