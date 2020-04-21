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
 * 菜品大类表
 */
@Entity
@Table(name = "h_dish_big")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DishBig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    @Column
    private Long dSId;  // SuperDish外键

    @Column(length = 20)
    private String bigName; // 所属大类别名：主食、蔬菜、水果、畜禽肉类、鱼虾类、奶制品、大豆及坚果类、油、盐

    @Column(length = 20)
    private String smallName;   // 大类中的小类：主食-->谷物、薯类等

    @Column(length = 50)
    private String icon;    // 记录Icon名

    @Column
    private String instructions;    // 说明书


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
