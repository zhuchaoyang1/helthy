package com.zcy.cn.dao;

import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.vo.ArticlePingVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticlePingDao extends JpaRepository<ArticlePing, Long> {

    @Query(value = "SELECT new com.zcy.cn.vo.ArticlePingVO(ap,us) FROM ArticlePing ap " +
            "LEFT JOIN Users us ON ap.uId = us.uId WHERE ap.iAId = :iAId ORDER BY ap.aPId DESC")
    List<ArticlePingVO> findAllByiAIdOrderByaPIdDesc(@Param(value = "iAId") Long iAId, Pageable pageable);

}
