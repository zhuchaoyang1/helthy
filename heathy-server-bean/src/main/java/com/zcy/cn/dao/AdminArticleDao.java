package com.zcy.cn.dao;

import com.zcy.cn.bean.AdminArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminArticleDao extends JpaRepository<AdminArticle, Long> {

    @Query(value = "SELECT aa FROM AdminArticle aa ORDER BY aa.id DESC")
    Page<AdminArticle> queryByPage(Pageable pageable);

    Optional<AdminArticle> findById(Long id);

}
