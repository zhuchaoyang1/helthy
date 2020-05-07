package com.zcy.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.ArticlePingService;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.ArticlePingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ping")
public class ArticlePingController {

    @Autowired
    private ArticlePingService articlePingService;

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
     * @param iAId      文章ID
     * @param pageNo    分页起始页
     * @param frontSize s上一次查询记录的条数
     * @return
     */
    @GetMapping("/{iAId}/{pageNo}/{frontSize}")
    public ResultHttp queryByArticleIdByPage(@PathVariable String iAId, @PathVariable String pageNo, @PathVariable String frontSize) {
        // TODO 评论问题
        int start = Integer.parseInt(pageNo);
        start = start == 0 ? 0 : start - 1;
        int frontSizeInt = Integer.parseInt(frontSize);
        Pageable pageable = PageRequest.of(start, 5);
        List<ArticlePingVO> result = articlePingService.findAllByIAIdOrderByAPIdDesc(Long.valueOf(iAId), pageable);
        if (result.size() != frontSizeInt) {
            // 当前页数量有更新
            return ResultHttp.builder().code(1).result(
                    JSON.toJSONString(result.subList(0, (result.size() - frontSizeInt) > 0 ? (result.size() - frontSizeInt) : result.size()), SerializerFeature.DisableCircularReferenceDetect)
            ).build();
        } else {
            // 当前页数量没有更新
            pageable = PageRequest.of(start + 1, 5);
            result = articlePingService.findAllByIAIdOrderByAPIdDesc(Long.valueOf(iAId), pageable);
            return ResultHttp.builder().code(1).result(
                    JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect)
            ).build();
        }

    }


}
