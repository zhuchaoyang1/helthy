package com.zcy.cn.pages;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 为了保证小程序消息具有实时性
 * 不能同普通Web界面的分页方式
 */
@Component
public class WechatPage<T> {

    /**
     * 根据前一个FrontSize进行分割
     * 并返回最后的Size
     *
     * @param frontSize 前一页的条数
     * @param datas     当前页的数据源
     * @return
     */
    public Map<String, Object> splitPage(int frontSize, List<T> datas) {
        Map<String, Object> result = new HashMap<>();
        List<Object> splitList = new ArrayList<>();
        int frontDataSize = datas.size();
        if (frontDataSize == frontSize) {
            // 说明当前页数据没有更新
            result.put("flag", false);
            result.put("splitList", splitList);
            return result;
        }

        result.put("flag", true);
        splitList.addAll(datas.subList(frontSize, datas.size()));
        result.put("splitList", splitList);
        return result;
    }
}
