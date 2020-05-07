package com.zcy.cn.dao;

import com.zcy.cn.bean.IssueTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueTagDao extends JpaRepository<IssueTag, Long> {

    Optional<IssueTag> findByTagName(String tageName);

    Optional<IssueTag> findByisId(Long isId);

}
