package com.zcy.cn.service.impl;

import com.zcy.cn.bean.ArticlePing;
import com.zcy.cn.dao.ArticlePingDao;
import com.zcy.cn.service.ArticlePingService;
import com.zcy.cn.vo.ArticlePingVO;
import com.zcy.cn.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ArticlePingServiceImpl implements ArticlePingService {

    @Autowired
    private ArticlePingDao articlePingDao;

    @Override
    public ArticlePing save(ArticlePing articlePing) {
        return articlePingDao.save(articlePing);
    }

    @Override
    public List<ArticlePingVO> findAllByIAIdOrderByAPIdDesc(Long iAId, Pageable pageable) {
        List<ArticlePingVO> result = articlePingDao.findAllByiAIdOrderByaPIdDesc(iAId, pageable).stream().map(var -> {
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(var.getUsers(), usersVO);
            var.setUsers(null);
            var.setUsersVO(usersVO);
            return var;
        }).collect(Collectors.toList());
        return result;
    }

}
