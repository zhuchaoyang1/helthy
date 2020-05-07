package com.zcy.cn.vo;

import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.bean.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class IssueArticleVO {

    private Long iAId;

    private Long issueId;                           // 标签ID

    private String issueTagName;                    // 标签名称

    private String articleDetailText;               // 文章Text详情

    private String articleDetailHtml;               // 文章Html详情

    private Date publishDate;                       // 发表日期

    private String publishDateStr;                  // 发表日期String类型s

    private String publisherName;                   // 文章发布者  匿名 或者 微信昵称

    private Long pubId;                             // 文章发布者ID

    private String avatarUrl;                       // 发布者头像

    private String gender;                          // 发布者性别

    private Boolean isAnonymous;                    // 是否匿名  True:匿名 False:否匿名

    private IssueArticle issueArticle;

    private String title;                           // 文章标题

    private String abstracts;                       // 文章摘要

    private Users users;

    public IssueArticleVO() {
    }

    public IssueArticleVO(IssueArticle issueArticle, Users users) {
        this.issueArticle = issueArticle;
        this.users = users;
    }


    public IssueArticle createIssueArticle() {
        IssueArticle issueArticle = new IssueArticle();
        BeanUtils.copyProperties(this, issueArticle);
        return issueArticle;
    }

}
