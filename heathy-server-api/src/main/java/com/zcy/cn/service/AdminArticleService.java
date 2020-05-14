package com.zcy.cn.service;

import com.zcy.cn.bean.AdminArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminArticleService {

    AdminArticle save(AdminArticle adminArticle);

    Page<AdminArticle> queryByPage(Pageable pageable);

}
