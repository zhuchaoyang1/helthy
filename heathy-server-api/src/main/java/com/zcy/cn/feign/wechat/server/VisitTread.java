package com.zcy.cn.feign.wechat.server;

import config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component
public interface VisitTread {

    /**
     * 周趋势
     * POST
     * https://api.weixin.qq.com/datacube/getweanalysisappidweeklyvisittrend?access_token=ACCESS_TOKEN
     */
    @Component
    @FeignClient(value = "visitedtread", configuration = FeignConfiguration.class, url = "${wechat.server}")
    interface Visit {
        @RequestMapping(method = RequestMethod.POST, value = "/datacube/getweanalysisappidweeklyvisittrend",
                consumes = MediaType.APPLICATION_JSON_VALUE)
        Object getVisitTread(@RequestParam("access_token") String access_token,
                             @RequestBody Map<String, String> map);
    }

    /**
     * 日趋势  用于前端日折线图
     * POST
     * POST https://api.weixin.qq.com/datacube/getweanalysisappiddailyvisittrend?access_token=ACCESS_TOKEN
     */
    @Component
    @FeignClient(value = "daliytread", configuration = FeignConfiguration.class, url = "${wechat.server}")
    interface DaliyTread {
        @RequestMapping(method = RequestMethod.POST, value = "/datacube/getweanalysisappiddailyvisittrend",
                consumes = MediaType.APPLICATION_JSON_VALUE)
        Object getDaliyVisitTread(@RequestParam("access_token") String access_token,
                             @RequestBody Map<String, String> map);
    }

}
