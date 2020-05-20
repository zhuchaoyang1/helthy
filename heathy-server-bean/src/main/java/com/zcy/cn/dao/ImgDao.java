package com.zcy.cn.dao;

import com.zcy.cn.bean.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgDao extends JpaRepository<Img, Long> {

    List<Img> findByIndexs(Integer indexs);

    Img findByLocaltionPath(String localtionPath);

}
