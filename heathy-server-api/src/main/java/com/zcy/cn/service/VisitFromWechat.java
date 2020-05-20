package com.zcy.cn.service;

import java.util.List;
import java.util.Map;

public interface VisitFromWechat {

    Object getVisitTread(String access_token, Map<String, String> payLoad);

    Object getDaliyTread(String access_token, Map<String, String> payLoad);

    List<Object> currWeekTread();

    List<Object> lastWeekDaliyTread();
}
