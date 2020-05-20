package com.zcy.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.dto.PingLunPageDTO;
import com.zcy.cn.pages.WechatPage;
import com.zcy.cn.service.ArticlePingService;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.ArticlePingVO;
import com.zcy.cn.vo.IssueArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/ping")
public class ArticlePingController {

    @Autowired
    private ArticlePingService articlePingService;
    @Autowired
    private WechatPage<ArticlePingVO> wechatPage;

    @TokenModel
    @PostMapping("/save")
    public ResultHttp savePing(@RequestBody ArticlePing articlePing, HttpServletRequest request, AnnotationUser annotationUser) {
        articlePing.setUId(annotationUser.getUId());
        articlePing.setPingDate(new Date());
        articlePing.setPingDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return ResultHttp.builder().code(1).result(articlePingService.save(articlePing)).build();
    }

    /**
     * 此处前端PageInfo.pageNo由后端控制
     *
     * @return
     */
    @PostMapping("/query/by")
    public ResultHttp queryByArticleIdByPage(@RequestBody PingLunPageDTO pingLunPageDTO) {
        if (pingLunPageDTO == null) {
            return ResultHttp.builder().code(-1).result("参数不合法").build();
        }

        Map<String, Object> result = new HashMap<>();  // flag 前端PageNo是否需要+1   Data 前端需要concat的数据


        Pageable pageable = PageRequest.of(pingLunPageDTO.getPageNo(), 5);
        List<ArticlePingVO> currData = articlePingService.findAllByIAIdOrderByAPIdDesc(pingLunPageDTO.getIAId(), pageable);

        if (currData.size() < 5) {
            result.put("flag", false);  // 前端PageNo不要+1
        } else {
            result.put("flag", true);   // 当前查询页满了，  前端可以+1 下次从下一页开始查询
        }

        if (pingLunPageDTO.getFrontSize() == currData.size()) {
            // 前端当前页满了
            result.put("data", new ArrayList<>());
        } else {
            // 构造当前页的差值
            Map<String, Object> spitMap = wechatPage.splitPage(pingLunPageDTO.getFrontSize(), currData);
            result.put("data", spitMap.get("splitList"));
        }

        return ResultHttp.builder().code(1).result(JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect)).build();

    }


}
