package com.zcy.cn.service.impl;

import com.zcy.cn.bean.IssueArticle;
import com.zcy.cn.bean.PrivateLetter;
import com.zcy.cn.dao.IssueArticleDao;
import com.zcy.cn.dao.PrivateLetterDao;
import com.zcy.cn.service.PrivateLetterService;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.PrivateLetterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PrivateLetterServiceImpl implements PrivateLetterService {

    @Autowired
    private PrivateLetterDao privateLetterDao;
    @Autowired
    private IssueArticleDao issueArticleDao;

    @Override
    public PrivateLetter save(PrivateLetter privateLetter, AnnotationUser annotationUser) {
        Optional<IssueArticle> optionalIssueArticle = issueArticleDao.findById(privateLetter.getAId());
        if (optionalIssueArticle.isPresent()) {
            IssueArticle issueArticle = optionalIssueArticle.get();
            privateLetter.setDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            privateLetter.setHasLooked(false);
            privateLetter.setUIdByMe(issueArticle.getPubId());
            privateLetter.setUIdByOther(annotationUser.getUId());
            privateLetter.setTitles(issueArticle.getTitle());
        }
        return privateLetterDao.save(privateLetter);
    }

    @Override
    public Integer queryByNeverReadCount(Long meId) {
        return privateLetterDao.queryByNeverReadCount(meId);
    }

    @Override
    public List<PrivateLetterVO> queryByNeverRead(Long meId, Pageable pageable) {
        return privateLetterDao.queryByNeverRead(meId, pageable);
    }

    @Override
    public PrivateLetter findById(Long pLId) {
        Optional<PrivateLetter> privateLetterOptional = privateLetterDao.findById(pLId);
        if(privateLetterOptional.isPresent()) {
            PrivateLetter privateLetter = privateLetterOptional.get();
            privateLetter.setHasLooked(true);
            privateLetterDao.save(privateLetter);
        }
        return privateLetterDao.findById(pLId).orElse(null);
    }

}
