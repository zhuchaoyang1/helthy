package com.zcy.cn.controller;

import com.zcy.cn.bean.Img;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.CommonFileService;
import com.zcy.cn.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用文件接口
 */
@RestController
@RequestMapping("/common/file")
public class CommonFileController {

    @Autowired
    private ImgService imgService;
    @Autowired
    private CommonFileService commonFileService;

    /**
     * @param multipartFile
     * @param distinct      0:系统文件   1:客户端文章中图片
     * @param indexs
     * @param name
     * @param session
     * @return
     */
    @PostMapping("/save/file/{distinct}")
    public ResultHttp saveFile(@RequestParam("multipartFile") MultipartFile multipartFile,
                               @PathVariable Integer distinct,
                               @RequestParam(value = "indexs", required = false) Integer indexs,
                               @RequestParam(value = "name", required = false) String name,
                               HttpSession session) {
        Object result = null;
        switch (distinct) {
            case 0: {
                // 系统资源信息
                result = imgService.saveImg(multipartFile, session, Img.builder().indexs(indexs).name(name).build(), false);
                break;
            }
            case 1:
                // 平台文章中资源信息
                result = imgService.saveImg(multipartFile, session, Img.builder().indexs(indexs).name(name).build(), true);
        }
        return ResultHttp.builder().code(1).result(result).build();
    }

    /**
     * @param distinct 用于区分资源信息
     * @param indexs
     * @return
     */
    @GetMapping("/{distinct}/{indexs}")
    public ResultHttp findFilePath(@PathVariable Integer distinct, @PathVariable Integer indexs) {
        List<String> paths = new ArrayList<>();
        switch (distinct) {
            case 0: {
                List<Img> imgs = imgService.findByIndexs(indexs);
                for (Img img : imgs) {
                    paths.add(img.getLocaltionPath());
                }
            }
        }
        return ResultHttp.builder().code(1).result(paths).build();
    }


    @DeleteMapping("/delete/{distinct}")
    public ResultHttp deleteFileBatch(@RequestBody List<String> filePaths, @PathVariable Integer distinct, HttpSession session) {
        // 删除文件
        commonFileService.deleteBatch(filePaths, session);
        // 删除表数据
        switch (distinct) {
            // 系统文件
            case 0:
                // 文章中图片
            case 1: {
                imgService.delete(filePaths);
                break;
            }
        }
        return ResultHttp.builder().code(1).result("删除成功").build();
    }

}
