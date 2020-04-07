package com.zcy.cn.vo;

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
 * 用户表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersVO {

    private String nickName;

    private String avatarUrl;

    private String gender;

    private String country;

    private String province;

    private String city;

    /**
     * country下province下city的语言
     */
    private String language;

    private String openId;

}
