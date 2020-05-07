package com.zcy.cn.dao;

import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.vo.IssueArticleVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueArticleDao extends JpaRepository<IssueArticle, Long> {

    /**
     * 按TagName查询
     *
     * @param issueTagName
     * @param pageable
     * @return
     */
    @Query(value = "SELECT new com.zcy.cn.vo.IssueArticleVO(issue,u) FROM IssueArticle issue " +
            "LEFT JOIN Users u ON u.uId = issue.pubId WHERE issue.issueTagName = :issueTagName ORDER BY issue.iAId DESC ")
    List<IssueArticleVO> queryArticleAndUserInfoByTag(@Param(value = "issueTagName") String issueTagName, Pageable pageable);

    /**
     * 不分TagName查询
     *
     * @param pageable
     * @return
     */
    @Query(value = "SELECT new com.zcy.cn.vo.IssueArticleVO(issue,u) FROM IssueArticle issue " +
            "LEFT JOIN Users u ON u.uId = issue.pubId ORDER BY issue.iAId DESC")
    List<IssueArticleVO> queryArticleAndUserInfoByTag(Pageable pageable);


    @Query(value = "SELECT new com.zcy.cn.vo.IssueArticleVO(issue,u) FROM IssueArticle issue " +
            "LEFT JOIN Users u ON u.uId = issue.pubId WHERE issue.iAId = :iAId")
    IssueArticleVO queryUserAndIssureById(@Param(value = "iAId") Long iAId);
}
