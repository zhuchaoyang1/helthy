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
 * 根据BMI几种状态 定制化 主食食用量
 * 瘦、偏瘦、标准呢、偏胖、肥胖、极度肥胖
 */
@Data
@Entity
@Table(name = "h_custom")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Custom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    @Column
    private Long sId;     // super大类别表外键

    /**
     * 0：瘦
     * 1：偏瘦
     * 2：标准
     * 3：偏胖
     * 4：肥胖
     * 5：极度肥胖
     */
    @Column(length = 3)
    private Integer bmiFlag;

    @Column(columnDefinition = "double (7,2)")
    private Double cWeight;

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
