package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.AccessTokenService;
import com.zcy.cn.service.VisitFromWechat;
import com.zcy.cn.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对接微信厂商访问分析
 */
@RestController
@RequestMapping("/visit")
public class WechatController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private VisitFromWechat visitFromWechat;


    /**
     * 周趋势
     *
     * @return
     */
    @PostMapping("/tread/{symbol}")
    public ResultHttp getVisitTread(@PathVariable String symbol) {

        LocalDate today = LocalDate.now();

        LocalDate startDate = null, endDate = null;

        switch (symbol) {
            case "1": {
                // 前一周
                endDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
                startDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).minusDays(6);
                break;
            }
            case "2": {
                // 前两周
                endDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).minusDays(7);
                startDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).minusDays(13);
                break;
            }
            default: {
                // 前一周
                endDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
                startDate = today.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).minusDays(6);
            }
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");

        Map<String, String> payLoad = new HashMap<>();
        payLoad.put("begin_date", df.format(startDate));
        payLoad.put("end_date", df.format(endDate));
        String accessToken = accessTokenService.getAccessTokenFromRedis().toString();
        return ResultHttp.builder().code(1).result(visitFromWechat.getVisitTread(accessToken, payLoad)).build();
    }

    /**
     * 查询本周变化趋势
     * 获取上周变化趋势
     * last: true表示上周  false表示本周
     *
     * @return
     */
    @GetMapping("/tread/daliy/{last}")
    public ResultHttp getCurrWeekTread(@PathVariable String last) {
        boolean isLastWeek = Boolean.valueOf(last);
        return ResultHttp.builder().code(1).result(isLastWeek ? visitFromWechat.lastWeekDaliyTread() : visitFromWechat.currWeekTread()).build();
    }

}
