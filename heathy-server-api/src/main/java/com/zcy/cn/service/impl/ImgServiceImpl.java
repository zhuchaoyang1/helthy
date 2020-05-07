package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Img;
import com.zcy.cn.dao.ImgDao;
import com.zcy.cn.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ImgServiceImpl implements ImgService {

    @Value("${local.upload.img}")
    private String uploadProfile;

    @Autowired
    private ImgDao imgDao;

    public Page<Img> queryAllByPage(Pageable pageable) {
        return imgDao.findAll(pageable);
    }

    @Override
    public Img saveImg(MultipartFile file, HttpSession session, Img img, Boolean isArcticle) {
        String originalFileName = file.getOriginalFilename();
        String newFileName;
        String staff = originalFileName.substring(originalFileName.lastIndexOf("."));
        if (isArcticle) {
            newFileName = "/img/arcticle/" + UUID.randomUUID() + staff;
        } else {
            newFileName = "/img/" + UUID.randomUUID() + originalFileName;
        }
        String logoRealPath = System.getProperty("user.dir") + uploadProfile;
        File newFile = new File(logoRealPath + newFileName);
        try {
            if (!newFile.exists()) {
                newFile.mkdirs();
            } else {
                return null;
            }
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("\n小程序资源图片上传失败，失败原因:\n{}", e.getCause());
            return null;
        }
        img.setLocaltionPath(newFileName);
        return imgDao.save(img);
    }

    @Override
    public Img findByIndexs(Integer indexs) {
        return imgDao.findByIndexs(indexs);
    }

    @Override
    public void delete(List<String> filePaths) {
        filePaths.parallelStream().forEach(var -> {
            Img img = imgDao.findByLocaltionPath(var);
            if (img != null) {
                imgDao.delete(img);
            }
        });
    }
}
