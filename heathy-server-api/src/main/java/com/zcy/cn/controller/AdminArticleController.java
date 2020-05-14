package com.zcy.cn.controller;

import com.zcy.cn.bean.AdminArticle;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.AdminArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private AdminArticleService adminArticleService;

    @PostMapping("/save")
    public ResultHttp save(@RequestBody AdminArticle adminArticle) {
        Date date = new Date();
        adminArticle.setWriterDateTime(date);
        adminArticle.setWriterDateTimeStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        return ResultHttp.builder().code(1).result(adminArticleService.save(adminArticle)).build();
    }

    @GetMapping("/{pageNo}")
    public ResultHttp queryByPage(@PathVariable String pageNo) {
        if (StringUtils.isEmpty(pageNo)) {
            return ResultHttp.builder().code(-1).result("参数不合法").build();
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), 15);
        return ResultHttp.builder().code(1).result(adminArticleService.queryByPage(pageable)).build();
    }

}
