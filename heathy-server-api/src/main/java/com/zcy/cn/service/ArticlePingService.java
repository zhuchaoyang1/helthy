package com.zcy.cn.service;

import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.vo.ArticlePingVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticlePingService {

    ArticlePing save(ArticlePing articlePing);

    List<ArticlePingVO> findAllByIAIdOrderByAPIdDesc(Long iAId, Pageable pageable);

}
