package com.zcy.cn.service.impl;

import com.zcy.cn.bean.BodyArgs;
import com.zcy.cn.dao.BodyArgsDao;
import com.zcy.cn.service.BodyArgsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BodyArgsServiceImpl implements BodyArgsService {

    @Autowired
    private BodyArgsDao bodyArgsDao;

    public BodyArgs save(BodyArgs bodyArgs) {
        return bodyArgsDao.save(bodyArgs);
    }

    @Override
    public List<BodyArgs> findAllByUId(Long uId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "bId");
        Pageable pageable = PageRequest.of(0, 4, sort);
        return bodyArgsDao.findByuId(uId, pageable);
    }

    @Override
    public List<Date> findUpdateTimeById(Long uid) {
        return bodyArgsDao.findCreatedTimeById(uid);
    }

    @Override
    public BodyArgs findLastByUId(Long uId) {
        return bodyArgsDao.findLastBody(uId);
    }

}
