package com.zcy.cn.dao;

import com.zcy.cn.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<Users, Long> {

    Users findByOpenId(String openId);

}
