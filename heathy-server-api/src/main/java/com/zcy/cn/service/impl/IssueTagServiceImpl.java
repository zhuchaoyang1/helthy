package com.zcy.cn.service.impl;

import com.zcy.cn.bean.IssueTag;
import com.zcy.cn.dao.IssueTagDao;
import com.zcy.cn.service.IssueTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class IssueTagServiceImpl implements IssueTagService {

    @Autowired
    private IssueTagDao issueTagDao;

    @Override
    public List<IssueTag> saveBatch(List<String> tagNames) {
        List<IssueTag> issueTags = new ArrayList<>();
        tagNames.parallelStream().forEach(var -> {
            if (!issueTagDao.findByTagName(var).isPresent()) {
                // 避免Tag重复保存
                issueTags.add(IssueTag.builder().tagName(var).build());
            }
        });
        return issueTagDao.saveAll(issueTags);
    }

    @Override
    public List<IssueTag> queryAll() {
        return issueTagDao.findAll();
    }
}
