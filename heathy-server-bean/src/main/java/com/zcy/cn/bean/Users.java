package com.zcy.cn.bean;

import com.zcy.cn.vo.UsersVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "h_users")
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uId;

    @Column
    private String nickName;

    @Column
    private String avatarUrl;

    @Column
    private String gender;

    @Column
    private String country;

    @Column
    private String province;

    @Column
    private String city;

    /**
     * country下province下city的语言
     */
    @Column
    private String language;

    @Column
    private String openId;

    /**
     * 创建时间
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

    // Copy
    public UsersVO createUserVO() {
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(this, usersVO, "uId", "openId");
        return usersVO;
    }

}
