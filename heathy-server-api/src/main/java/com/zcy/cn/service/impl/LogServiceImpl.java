package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.dao.LogsDao;
import com.zcy.cn.enums.LogEnum;
import com.zcy.cn.enums.PlatformsEnum;
import com.zcy.cn.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogsDao logsDao;

    @Override
    public boolean save(Logs logs) {
        // True express that Log Leavel is not Correctly
        boolean leavelCurrect = Arrays.asList(LogEnum.values()).stream().filter(var ->
                logs.getLeavel().toUpperCase().equals(var.getLeavel())
        ).collect(Collectors.toList()).isEmpty();

        boolean platCurrect = Arrays.asList(PlatformsEnum.values()).stream().filter(var ->
                logs.getPlatforms().toUpperCase().equals(var.getPlatforms())
        ).collect(Collectors.toList()).isEmpty();

        if (!leavelCurrect && !platCurrect) {
            logs.setLeavel(logs.getLeavel().toUpperCase());
            logs.setHasSendEmail(false);
            logsDao.save(logs);
        }
        return leavelCurrect || platCurrect;
    }

    @Override
    public Page<Logs> findAllByLeavelAndHasSendEmail(String leavel, boolean hasSendEmail) {
        Sort sort = Sort.by(Sort.Direction.DESC, "lId");
        Pageable pageable = PageRequest.of(0, 20, sort);
        return logsDao.findAllByLeavelAndHasSendEmail(leavel.toUpperCase(), hasSendEmail, pageable);
    }

    @Override
    public Optional<Logs> findById(Long id) {
        return logsDao.findById(id);
    }

    @Override
    public void updateLogsSendEmail(Boolean status, Long id) {
        logsDao.updateLogsSendEmail(status, id);
    }
}
