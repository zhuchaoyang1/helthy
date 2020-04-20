package com.zcy.cn.dao;

import com.zcy.cn.bean.BodyArgs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BodyArgsDao extends JpaRepository<BodyArgs, Long> {

    // 根据UId查询查询四条记录
    List<BodyArgs> findByuId(Long uid, Pageable pageable);

    @Query(value = "SELECT bodys.createTime FROM BodyArgs bodys WHERE bodys.uId = :uId")
    List<Date> findCreatedTimeById(@Param("uId") Long uid);

}
