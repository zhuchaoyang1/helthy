package com.zcy.cn.dao;

import com.zcy.cn.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface UserDao extends JpaRepository<Users, Long> {

    Users findByOpenId(String openId);

    @Transactional
    @Modifying
    @Query("update Users u set u.nickName=111 where 1=1")
    void update();

}
