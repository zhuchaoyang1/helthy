package com.zcy.cn.service;

import com.zcy.cn.bean.PrivateLetter;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.PrivateLetterVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivateLetterService {

    PrivateLetter save(PrivateLetter privateLetter, AnnotationUser annotationUser);

    /**
     * 关于我未读条数
     *
     * @param meId
     * @return
     */
    Integer queryByNeverReadCount(Long meId);

    /**
     * 关于我的信息
     *
     * @param meId
     * @return
     */
    List<PrivateLetterVO> queryByNeverRead(Long meId, Pageable pageable);

    PrivateLetter findById(Long pLId);
}
