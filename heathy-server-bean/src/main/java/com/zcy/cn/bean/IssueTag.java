package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 悄悄话顶部分类
 */
@Data
@Entity
@Table(name = "h_issue_tag")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class IssueTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long isId;

    @Column(length = 10)
    private String tagName;

}
