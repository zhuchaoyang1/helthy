package com.zcy.cn.vo;

import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.bean.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePingVO {

    private ArticlePing articlePing;

    private Users users;

    private UsersVO usersVO;

    public ArticlePingVO(ArticlePing articlePing, Users users) {
        this.articlePing = articlePing;
        this.users = users;
    }

}
