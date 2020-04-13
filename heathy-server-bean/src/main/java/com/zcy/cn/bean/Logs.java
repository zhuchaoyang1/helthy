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
 * 日志级别字段暂且先做扩展处理
 */
@Data
@Entity
@Table(name = "h_logs")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lId;

    // Error发现者
    @Column
    private Long uId;

    // WECHAT  WEB  日志来源
    @Column(length = 20)
    private String platforms;

    // 日志信息
    @Column(columnDefinition = "blob")
    private String logInfo;

    // 日志级别
    @Column(length = 10)
    private String leavel;

    // 是否已经发送邮件告警
    @Column(columnDefinition = "bit default 0")
    private Boolean hasSendEmail;

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
