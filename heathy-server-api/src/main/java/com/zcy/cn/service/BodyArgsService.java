package com.zcy.cn.service;

import com.zcy.cn.bean.BodyArgs;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BodyArgsService {

    BodyArgs save(BodyArgs bodyArgs);

    List<BodyArgs> findAllByUId(Long uId);

    List<Date> findUpdateTimeById(Long uid);

    BodyArgs findLastByUId(Long uId);
}
