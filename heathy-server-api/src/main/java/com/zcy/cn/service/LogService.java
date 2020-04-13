package com.zcy.cn.service;

import com.zcy.cn.bean.Logs;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface LogService {

    boolean save(Logs logs);

    Page<Logs> findAllByLeavelAndHasSendEmail(String leavel, boolean hasSendEmail);

    Optional<Logs> findById(Long id);

    void updateLogsSendEmail(Boolean status, Long id);

}
