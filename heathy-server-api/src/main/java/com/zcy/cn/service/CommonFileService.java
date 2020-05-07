package com.zcy.cn.service;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommonFileService {

    void deleteBatch(List<String> filePaths, HttpSession httpSession);

}
