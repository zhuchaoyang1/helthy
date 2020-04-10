package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Logs;
import com.zcy.cn.dao.LogsDao;
import com.zcy.cn.enums.LogEnum;
import com.zcy.cn.enums.PlatformsEnum;
import com.zcy.cn.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
}
