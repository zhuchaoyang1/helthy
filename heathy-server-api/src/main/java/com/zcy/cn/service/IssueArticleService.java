package com.zcy.cn.service;

import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.ArcticlePageVO;
import com.zcy.cn.vo.IssueArticleVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IssueArticleService {

    IssueArticle saveArticle(IssueArticleVO issueArticleVO, AnnotationUser annotationUser);

    List<IssueArticleVO> queryByTagName(ArcticlePageVO arcticlePageVO, Pageable pageable);

    IssueArticleVO queryUserAndIssureById(Long iAId);

}
