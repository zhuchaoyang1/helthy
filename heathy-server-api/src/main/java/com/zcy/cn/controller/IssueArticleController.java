package com.zcy.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.IssueArticleService;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.ArcticlePageVO;
import com.zcy.cn.vo.IssueArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/issue/article")
public class IssueArticleController {

    @Autowired
    private IssueArticleService issueArticleService;

    @TokenModel
    @PostMapping("/write")
    public ResultHttp save(@RequestBody IssueArticleVO issueArticleVO, HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(issueArticleService.saveArticle(issueArticleVO, annotationUser)).build();
    }

    /**
     * 约定
     * TagId为-1时进行查询全部 不分标签
     *
     * @param
     * @return
     */
    @PostMapping("/query/page")
    public ResultHttp queryByTagName(@RequestBody ArcticlePageVO arcticlePageVO) {
        // 构造分页信息
        Pageable pageable = PageRequest.of(arcticlePageVO.getPageNo(), 10, Sort.by(Sort.Direction.DESC, "iAId"));
        return ResultHttp.builder().code(1).result(
                JSON.toJSONString(issueArticleService.queryByTagName(arcticlePageVO, pageable), SerializerFeature.DisableCircularReferenceDetect)
        ).build();
    }

    @GetMapping("/{iAId}")
    public ResultHttp queryById(@PathVariable String iAId) {
        return ResultHttp.builder().code(1).result(issueArticleService.queryUserAndIssureById(Long.valueOf(iAId))).build();
    }
}
