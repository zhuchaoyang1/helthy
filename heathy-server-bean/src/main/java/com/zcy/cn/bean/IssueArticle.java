package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "h_issue_article")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class IssueArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iAId;

    @Column
    private Long issueId;                           // 标签ID

    @Column(length = 20)
    private String issueTagName;                    // 标签名称

    @Column(columnDefinition = "blob")
    private String articleDetailText;               // 文章详情 Text对象

    @Column(columnDefinition = "blob")
    private String articleDetailHtml;

    @Column
    private Date publishDate;                       // 发表日期

    @Column(length = 50)
    private String publisherName;                   // 文章发布者  匿名 或者 微信昵称

    @Column
    private Long pubId;                             // 文章发布者ID

    @Column
    private Boolean isAnonymous;                    // 是否匿名  True:匿名 False:否匿名

    @Column
    private String title;                           // 文章标题

    @Column
    private String abstracts;                       // 文章摘要

}
