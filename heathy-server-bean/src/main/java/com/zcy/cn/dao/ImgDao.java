package com.zcy.cn.dao;

import com.zcy.cn.bean.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgDao extends JpaRepository<Img, Long> {

    Img findByIndexs(Integer indexs);

    Img findByLocaltionPath(String localtionPath);

}
