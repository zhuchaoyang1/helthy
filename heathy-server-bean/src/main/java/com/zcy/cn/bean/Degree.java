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

@Data
@Entity
@Table(name = "h_record_percent")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dgId;

    @Column
    private Long uId;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dates;

    @Column(columnDefinition = "double (8,3)")
    private Double degreePercent;

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
