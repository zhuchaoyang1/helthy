package com.zcy.cn.controller;

import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.IssueTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/issue")
public class IssueTagController {

    @Autowired
    private IssueTagService issueTagService;


    @PostMapping("/save/batch")
    public ResultHttp save(@RequestBody List<String> issueTypes) {
        return ResultHttp.builder().code(1).result(issueTagService.saveBatch(issueTypes)).build();
    }

    @GetMapping("/query")
    public ResultHttp queryAll() {
        return ResultHttp.builder().code(1).result(issueTagService.queryAll()).build();
    }


}
