package com.zcy.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.AdminArticle;
import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.pages.WechatPage;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/issue/article")
public class IssueArticleController {

    @Autowired
    private IssueArticleService issueArticleService;
    @Autowired
    private WechatPage<IssueArticleVO> wechatPage;

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
        if (arcticlePageVO == null) {
            return ResultHttp.builder().code(-1).result("参数不合法").build();
        }

        Map<String, Object> result = new HashMap<>();  // flag 前端PageNo是否需要+1   Data 前端需要concat的数据

        // 构造分页信息
        Pageable pageable = PageRequest.of(arcticlePageVO.getPageNo(), 10, Sort.by(Sort.Direction.DESC, "iAId"));
        List<IssueArticleVO> currData = issueArticleService.queryByTagName(arcticlePageVO, pageable);


        if (currData.size() < 10) {
            result.put("flag", false);  // 前端PageNo不要+1
        } else {
            result.put("flag", true);   // 当前查询页满了，  前端可以+1 下次从下一页开始查询
        }

        if (arcticlePageVO.getFrontSize() == currData.size()) {
            // 前端当前页满了
            result.put("data", new ArrayList<>());
        } else {
            // 构造当前页的差值
            Map<String, Object> spitMap = wechatPage.splitPage(arcticlePageVO.getFrontSize(), currData);
            result.put("data", spitMap.get("splitList"));
        }

        return ResultHttp.builder().code(1).result(JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect)).build();
    }

    @GetMapping("/{iAId}")
    public ResultHttp queryById(@PathVariable String iAId) {
        return ResultHttp.builder().code(1).result(issueArticleService.queryUserAndIssureById(Long.valueOf(iAId))).build();
    }
}
