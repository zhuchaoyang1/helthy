package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.Degree;
import com.zcy.cn.bean.DoneRecord;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.bean.SuperDish;
import com.zcy.cn.service.DegreeService;
import com.zcy.cn.service.DoneRecordService;
import com.zcy.cn.service.SuperDishService;
import com.zcy.cn.util.TimeUtil;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.DegreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/degree")
public class DegreeController {

    @Autowired
    private DegreeService degreeService;
    @Autowired
    private SuperDishService superDishService;
    @Autowired
    private DoneRecordService doneRecordService;
    private static NumberFormat nf = new DecimalFormat("0.0");

    @TokenModel
    @GetMapping("/seven")
    public ResultHttp querySevenData(HttpServletRequest request, AnnotationUser annotationUser) {
        List<Degree> result = degreeService.findCurrentWeek(annotationUser);
        List<DegreeVO> resultVO = new ArrayList<>();
        // 获取当周日期
        if (result.size() == 0) {
            // 进行当天数据的构造再次查询
            List<SuperDish> superDishes = superDishService.queryAll();
            List<DoneRecord> doneRecords = new ArrayList<>();
            for (SuperDish superDish : superDishes) {
                DoneRecord doneRecord = new DoneRecord();
                doneRecord.setUId(annotationUser.getUId());
                doneRecord.setPercent(0.0);
                doneRecord.setSId(superDish.getSId());
                doneRecords.add(doneRecord);
            }
            doneRecordService.save(doneRecords);
            result = degreeService.findCurrentWeek(annotationUser);
        }

        LocalDate forms = TimeUtil.dateConvertToLocalDate(result.get(0).getDates());
        // 一周七天的日期
        LocalDate[] currWeekLocalDate = TimeUtil.buildCurrWeekLocalDate(forms);
        for (int i = 0; i < currWeekLocalDate.length; i++) {
            DegreeVO degreeVO = new DegreeVO();
            LocalDate curr = currWeekLocalDate[i];
            List<Degree> tempList = result.stream().filter(var -> TimeUtil.dateConvertToLocalDate(var.getDates()).equals(curr)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tempList)) {
                BeanUtils.copyProperties(tempList.get(0), degreeVO);
            } else {
                degreeVO.setDegreePercent(0.0D);
            }
            degreeVO.setDegreePercent(Double.parseDouble(nf.format(degreeVO.getDegreePercent())));
            degreeVO.setWeeks(TimeUtil.weekString()[i]);
            resultVO.add(degreeVO);
        }
        return ResultHttp.builder().code(1).result(resultVO).build();
    }

}
