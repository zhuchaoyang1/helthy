package com.zcy.cn.dao;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.bean.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LogsDao extends JpaRepository<Logs, Long> {

    Page<Logs> findAllByLeavelAndHasSendEmail(String leavel, boolean hasSendEmail, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Logs log SET log.hasSendEmail = :status WHERE log.lId = :lid")
    void updateLogsSendEmail(@Param("status") Boolean status, @Param("lid") Long lid);

}
