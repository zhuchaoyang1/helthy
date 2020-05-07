package com.zcy.cn.service;

import com.zcy.cn.bean.IssueTag;

import java.util.List;

public interface IssueTagService {

    List<IssueTag> saveBatch(List<String> tagNames);

    List<IssueTag> queryAll();

}
