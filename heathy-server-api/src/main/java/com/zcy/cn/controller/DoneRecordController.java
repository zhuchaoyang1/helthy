package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Degree;
import com.zcy.cn.bean.DoneRecord;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.service.DegreeService;
import com.zcy.cn.service.DoneRecordService;
import com.zcy.cn.service.SuperDishService;
import com.zcy.cn.vo.AnnotationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/done/record")
public class DoneRecordController {

    @Autowired
    private DoneRecordService doneRecordService;
    @Autowired
    private SuperDishService superDishService;
    @Autowired
    private DegreeService degreeService;

    @TokenModel
    @PostMapping
    public ResultHttp saveOrUpdateRecord(@RequestBody List<DoneRecord> doneRecords, HttpServletRequest request, AnnotationUser annotationUser) {
        int superCount = superDishService.getSuperCount();
        double pingFen = 100.0 / superCount;
        double[] todayDonePercent = {0.0d};
        doneRecords.forEach(var -> {
            var.setDates(new Date());
            var.setUId(annotationUser.getUId());
            todayDonePercent[0] += pingFen * var.getPercent() / 100;
        });
        // 保存百分比入库
        Degree degree = Degree.builder().degreePercent(todayDonePercent[0]).dates(new Date())
                .uId(annotationUser.getUId()).build();
        Degree degreeFromData = degreeService.queryBySomeDay(annotationUser, new Date());
        if (degreeFromData != null) {
            degree.setDgId(degreeFromData.getDgId());
        }
        degreeService.saveOuUpdate(degree);
        return ResultHttp.builder().code(1).result(doneRecordService.save(doneRecords)).build();
    }

    /**
     * 获取一天的数据
     *
     * @param weekIndex
     * @param request
     * @param annotationUser
     * @return
     */
    @TokenModel
    @GetMapping("/{weekIndex}")
    public ResultHttp queryByUidAndDate(@PathVariable Integer weekIndex, HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(doneRecordService.queryByDateAndUid(annotationUser, weekIndex)).build();
    }

    /**
     * 计算饼图
     * 0 ~ 7 ： 7表示今天
     *
     * @param weekIndex
     * @param request
     * @param annotationUser
     * @returncaculateEachDondRecordPercent
     */
    @TokenModel
    @GetMapping("/cacl/{weekIndex}")
    public ResultHttp queryByUidAndDateAndCaclu(@PathVariable Integer weekIndex, HttpServletRequest request, AnnotationUser annotationUser) {
        return ResultHttp.builder().code(1).result(doneRecordService.caculateEachDondRecordPercent(annotationUser, weekIndex)).build();
    }
}
