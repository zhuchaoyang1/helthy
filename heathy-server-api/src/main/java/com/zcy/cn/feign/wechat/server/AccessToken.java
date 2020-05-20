package com.zcy.cn.feign.wechat.server;

import config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取小程序到微信厂商交互唯一ACCESSTOKEN凭证
 */
@Component
@FeignClient(value = "wechatserver", configuration = FeignConfiguration.class, url = "${wechat.server}")
public interface AccessToken {

    @RequestMapping(method = RequestMethod.GET, value = "/cgi-bin/token?grant_type=client_credential")
    String getAccessToken(@RequestParam("appid") String appid,
                          @RequestParam("secret") String secret);
}