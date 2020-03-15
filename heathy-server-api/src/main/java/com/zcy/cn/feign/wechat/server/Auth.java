package com.zcy.cn.feign.wechat.server;

import config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 根据小程序获取的Code换取微信Server OpenID和Session_key
 */
@Component
@FeignClient(value = "wechatauth", configuration = FeignConfiguration.class, url = "${wechat.server}")
public interface Auth {

    @RequestMapping(method = RequestMethod.GET, value = "/sns/jscode2session")
    String getOpenIdAndSessionKey(@RequestParam("appid") String appid,
                                  @RequestParam("secret") String secret,
                                  @RequestParam("js_code") String js_code,
                                  @RequestParam("grant_type") String grant_type);

}
