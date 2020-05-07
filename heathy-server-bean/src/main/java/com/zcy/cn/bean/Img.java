package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name = "h_img")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 图片说明序号
    @Column
    private Integer indexs;

    // 图片说明  如  无数据等字样
    @Column(length = 50)
    private String name;

    // 访问图片地址
    @Column(length = 150)
    private String localtionPath;

    @Column
    private Boolean isArcticle; // 是否是文章中的图片  True:文章中图片 False系统级图片

}
