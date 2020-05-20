package com.zcy.cn.service.impl;

import com.zcy.cn.feign.wechat.server.VisitTread;
import com.zcy.cn.service.AccessTokenService;
import com.zcy.cn.service.VisitFromWechat;
import com.zcy.cn.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class VisitFromWechatImpl implements VisitFromWechat {

    @Autowired
    private VisitTread.Visit visit;
    @Autowired
    private VisitTread.DaliyTread daliyTread;
    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public Object getVisitTread(String access_token, Map<String, String> payLoad) {
        return visit.getVisitTread(access_token, payLoad);
    }

    @Override
    public Object getDaliyTread(String access_token, Map<String, String> payLoad) {
        return daliyTread.getDaliyVisitTread(access_token, payLoad);
    }

    @Override
    public List<Object> currWeekTread() {
        String accessToken = accessTokenService.getAccessTokenFromRedis().toString();

        LocalDate[] currWeek = TimeUtil.buildCurrWeekLocalDate(LocalDate.now());
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        List<Object> result = new ArrayList<>();

        LocalDate[] untilWeek = new LocalDate[dayOfWeek.getValue() - 1];
        for (int i = 0; i < untilWeek.length; i++) {
            untilWeek[i] = currWeek[i];
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> currDateStr = new ArrayList();

        for (LocalDate localDate : untilWeek) {
            currDateStr.add(df.format(localDate));
        }

        Map<String, String> payLoad = new HashMap<>();
        currDateStr.stream().forEach(var -> {
            payLoad.put("begin_date", var);
            payLoad.put("end_date", var);
            result.add(this.getDaliyTread(accessToken, payLoad));
        });
        return result;
    }

    @Override
    public List<Object> lastWeekDaliyTread() {
        String accessToken = accessTokenService.getAccessTokenFromRedis().toString();
        List<Object> result = new ArrayList<>();

        LocalDate[] lastLocalDates = TimeUtil.buildLastWeek();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<String> currDateStr = new ArrayList();

        for (LocalDate localDate : lastLocalDates) {
            currDateStr.add(df.format(localDate));
        }

        Map<String, String> payLoad = new HashMap<>();
        currDateStr.stream().forEach(var -> {
            payLoad.put("begin_date", var);
            payLoad.put("end_date", var);
            result.add(this.getDaliyTread(accessToken, payLoad));
        });

        return result;
    }

}
