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
 * 记录每天完成任务情况
 */
@Data
@Entity
@Table(name = "h_done_record")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DoneRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dId;

    @Column
    private Long uId;

    @Column
    private Long sId;

    @Column(columnDefinition = "double (6,2)")
    private Double percent;

    @Temporal(TemporalType.DATE)
    @Column
    private Date dates;

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
