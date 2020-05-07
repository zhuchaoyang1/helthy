package com.zcy.cn.service;

import com.zcy.cn.bean.Img;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ImgService {

    Page<Img> queryAllByPage(Pageable pageable);

    Img saveImg(MultipartFile file, HttpSession session, Img img, Boolean isArcticle);

    Img findByIndexs(Integer indexs);

    void delete(List<String> filePaths);
}
