package com.zcy.cn.controller;

import com.zcy.cn.bean.AdminArticle;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.dto.PageInfo;
import com.zcy.cn.pages.WechatPage;
import com.zcy.cn.service.AdminArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private AdminArticleService adminArticleService;

    @Autowired
    private WechatPage<AdminArticle> wechatPage;

    @PostMapping("/save")
    public ResultHttp save(@RequestBody AdminArticle adminArticle) {
        Date date = new Date();
        adminArticle.setWriterDateTime(date);
        adminArticle.setWriterDateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        return ResultHttp.builder().code(1).result(adminArticleService.save(adminArticle)).build();
    }

    @GetMapping("/query/{id}")
    public ResultHttp queryById(@PathVariable String id) {
        return ResultHttp.builder().code(1).result(adminArticleService.findById(Long.valueOf(id))).build();
    }

    /**
     * 重要 前端收到PageNo + 1 的时候需要将frontSize = 0 置零
     *
     * @param pageInfo 包括最新一次一页的实际数据量大小   当前PageNo序号
     * @return
     */
    @PostMapping("/page")
    public ResultHttp queryByPage(@RequestBody PageInfo pageInfo) {
        if (pageInfo == null) {
            return ResultHttp.builder().code(-1).result("参数不合法").build();
        }

        Map<String, Object> result = new HashMap<>();  // flag 前端PageNo是否需要+1   Data 前端需要concat的数据
        Pageable pageable = PageRequest.of(pageInfo.getPageNo(), 15);
        List<AdminArticle> currData = adminArticleService.queryByPage(pageable).getContent();

        if (currData.size() < 15) {
            result.put("flag", false);  // 前端PageNo不要+1
        } else {
            result.put("flag", true);   // 当前查询页满了，  前端可以+1 下次从下一页开始查询
        }


        if (pageInfo.getFtontSize() == currData.size()) {
            // 前端当前页满了
            result.put("data", new ArrayList<>());
        } else {
            // 构造当前页的差值
            Map<String, Object> spitMap = wechatPage.splitPage(pageInfo.getFtontSize(), currData);
            result.put("data", spitMap.get("splitList"));
        }

        return ResultHttp.builder().code(1).result(result).build();
    }


}
