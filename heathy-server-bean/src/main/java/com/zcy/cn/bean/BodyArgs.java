package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户基本信息
 * 为了记录修改历史 每次修改均为Insert操作保留原来数据
 * 读取根据uId读取最新
 */
@Data
@Entity
@Table(name = "h_body")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BodyArgs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bId;

    @Column
    private Long uId;

    @Column
    private Integer standard;

    // 0  false 男  1  true 女
    @Column
    private Boolean gender;

    @Column(columnDefinition = "double (8,3)")
    private Double height;

    @Column(columnDefinition = "double (8,3)")
    private Double weight;

    @Column(length = 5)
    private String bodyStatus;

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
