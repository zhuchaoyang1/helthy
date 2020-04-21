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
 * 菜单超级大类
 */
@Entity
@Table(name = "h_super_dish")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SuperDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sId;

    @Column(length = 20)
    private String name;

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
