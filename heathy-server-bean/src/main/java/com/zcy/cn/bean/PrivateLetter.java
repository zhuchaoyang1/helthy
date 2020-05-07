package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 私信
 */
@Data
@Entity
@Table(name = "h_private_letter")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PrivateLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pLId;                  // 私信ID

    @Column
    private Long aId;                   // 文章ID

    @Column
    private Long uIdByMe;               // 被私信的人ID

    @Column
    private Long uIdByOther;            // 私信者ID

    @Column
    private Boolean hasLooked;          // 私信是否已经被查看

    @Column(length = 40)
    private String dateStr;             // 私信时间字符串

    @Column(length = 100)
    private String titles;              // 文章名称

    @Column(columnDefinition = "blob")
    private String siContent;           // 私信内容

    /**
     * 入库时间
     */
    @CreatedDate
    @Column
    private Date createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    @Column
    private Date modifyTime;


}
