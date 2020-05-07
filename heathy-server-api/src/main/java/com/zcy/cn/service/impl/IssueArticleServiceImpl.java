package com.zcy.cn.service.impl;

import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.bean.IssueTag;
import com.zcy.cn.dao.IssueArticleDao;
import com.zcy.cn.dao.IssueTagDao;
import com.zcy.cn.service.IssueArticleService;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.ArcticlePageVO;
import com.zcy.cn.vo.IssueArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class IssueArticleServiceImpl implements IssueArticleService {

    @Autowired
    private IssueArticleDao issueArticleDao;

    @Autowired
    private IssueTagDao issueTagDao;

    @Override
    public IssueArticle saveArticle(IssueArticleVO issueArticleVO, AnnotationUser annotationUser) {
        issueArticleVO.setPubId(annotationUser.getUId());
        issueArticleVO.setPublishDate(new Date());
        if (issueArticleVO.getIsAnonymous()) {
            issueArticleVO.setPublisherName("匿名");
        }
        return issueArticleDao.save(issueArticleVO.createIssueArticle());
    }

    @Override
    public List<IssueArticleVO> queryByTagName(ArcticlePageVO arcticlePageVO, Pageable pageable) {
        if (arcticlePageVO.getTagId() == -1) {
            // 约定 不分TagName
            return issueArticleDao.queryArticleAndUserInfoByTag(pageable).stream().map(var -> {
                var.setPublishDateStr(
                        new SimpleDateFormat("yyyy-MM-dd HH").format(var.getIssueArticle().getPublishDate()
                        ));
                return var;
            }).collect(Collectors.toList());
        }

        // 分TagName 根据TagId查询TagName
        Optional<IssueTag> issueTag = issueTagDao.findByisId(arcticlePageVO.getTagId());
        if (!issueTag.isPresent()) {
            return new ArrayList<>();
        }
        return issueArticleDao.queryArticleAndUserInfoByTag(issueTag.get().getTagName(), pageable).stream().map(var -> {
            var.setPublishDateStr(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(var.getIssueArticle().getPublishDate()
                    ));
            return var;
        }).collect(Collectors.toList());
    }

    @Override
    public IssueArticleVO queryUserAndIssureById(Long iAId) {
        IssueArticleVO issueArticleVO = issueArticleDao.queryUserAndIssureById(iAId);
        if (issueArticleVO != null) {
            issueArticleVO.setPublishDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(issueArticleVO.getIssueArticle().getPublishDate()));
        }
        return issueArticleVO;
    }
}
