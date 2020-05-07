package com.zcy.cn.service.impl;

import com.zcy.cn.service.CommonFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CommonFileServiceImpl implements CommonFileService {

    @Value("${local.upload.img}")
    private String uploadPath;

    @Override
    public void deleteBatch(List<String> filePaths, HttpSession httpSession) {
        String path = System.getProperty("user.dir") + uploadPath;
        // 提高并发
        filePaths.parallelStream().forEach(var -> {
            String fileRealPath = path + var;
            File file = new File(fileRealPath);
            if (file.exists()) {
                file.delete();
            }
        });
    }
}
