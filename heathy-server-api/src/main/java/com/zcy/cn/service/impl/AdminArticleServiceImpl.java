package com.zcy.cn.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zcy.cn.bean.AdminArticle;
import com.zcy.cn.dao.AdminArticleDao;
import com.zcy.cn.service.AdminArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private AdminArticleDao adminArticleDao;

    @Override
    public AdminArticle save(AdminArticle adminArticle) {
        return adminArticleDao.save(adminArticle);
    }

    @Override
    public Page<AdminArticle> queryByPage(Pageable pageable) {
        return adminArticleDao.queryByPage(pageable);
    }

    @Override
    public AdminArticle findById(Long id) {
        Optional<AdminArticle> optionalAdminArticle = adminArticleDao.findById(id);
        return optionalAdminArticle.orElse(null);
    }
}
