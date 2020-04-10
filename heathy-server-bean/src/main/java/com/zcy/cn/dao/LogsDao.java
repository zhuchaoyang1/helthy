package com.zcy.cn.dao;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.bean.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsDao extends JpaRepository<Logs, Long> {


}
