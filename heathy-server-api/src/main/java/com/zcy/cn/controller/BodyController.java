package com.zcy.cn.controller;

import com.zcy.cn.annotation.TokenModel;
import com.zcy.cn.bean.BodyArgs;
import com.zcy.cn.bean.ResultHttp;
import com.zcy.cn.bean.Users;
import com.zcy.cn.enums.BmiEnum;
import com.zcy.cn.service.BodyArgsService;
import com.zcy.cn.service.UserService;
import com.zcy.cn.util.BmiUtil;
import com.zcy.cn.util.StandardWeightUtil;
import com.zcy.cn.vo.AnnotationUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Body参数
 */
@RestController
@RequestMapping("/body")
@Api("Body参数")
public class BodyController {

    @Autowired
    private BodyArgsService bodyArgsService;
    @Autowired
    private BmiUtil bmiUtil;
    @Autowired
    private StandardWeightUtil standardWeightUtil;

    // 线程安全 --> SimpleDataFormatter非线程安全所以每次使用需要创建局部独立对象
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "保存Body参数", notes = "保存Body参数")
    @PostMapping("/save")
    @TokenModel
    public ResultHttp save(@RequestBody BodyArgs bodyArgs, HttpServletRequest request, AnnotationUser annotationUser) {
        bodyArgs.setUId(annotationUser.getUId());
        double bmi = bmiUtil.caculateBodyByBmi(bodyArgs);
        String bodyStatus = bmiUtil.caculateText(bodyArgs.getStandard(), bmi);
        bodyArgs.setBodyStatus(bodyStatus);
        return ResultHttp.builder().code(1).result(bodyArgsService.save(bodyArgs)).build();
    }

    @ApiOperation(value = "查询用户四条身体参数数据", notes = "并构造Echarts数据和标准体重数据")
    @GetMapping
    @TokenModel
    public ResultHttp myStandard(HttpServletRequest request, AnnotationUser annotationUser) {
        Map<String, Object> resultMap = new HashMap<>();

        List<BodyArgs> formDataByUId = bodyArgsService.findAllByUId(annotationUser.getUId());
        // 根据ID排序
        formDataByUId = formDataByUId.stream().sorted(Comparator.comparing(BodyArgs::getBId)).collect(Collectors.toList());

        // 节省前端渲染数据的开销
        // 横坐标时间点
        List<String> xAxisDate = new ArrayList<>();
        // 时间节点下计算标准体重
        List<Double> yAxisStandard = new ArrayList<>();
        // 时间节点下实际填报体重
        List<Double> yAxisTrue = new ArrayList<>();
        // 时间节点下的BMI指数
        List<Double> yAxisBim = new ArrayList<>();
        // 当前节点选择的BMI评判标准
        Integer[] stand = new Integer[1];

        if (formDataByUId.size() > 0) {
            formDataByUId.stream().forEach(var -> {
                Instant instant = var.getCreateTime().toInstant();
                // 时间节点
                xAxisDate.add(formatter.format(instant.atZone(ZoneId.systemDefault()).toLocalDate()));
                // 标准体重
                yAxisStandard.add(standardWeightUtil.caculateStandardWeight(var));
                // 实际体重
                yAxisTrue.add(var.getWeight());
                // bmi 体重(千克)/身高的平方(米)
                yAxisBim.add(bmiUtil.caculateBodyByBmi(var));
                stand[0] = var.getStandard();
            });

            double last = yAxisStandard.get(yAxisStandard.size() - 1);
            double low = last - last * 0.1;
            low = Math.round(low * 100.0) / 100.0;
            double high = last + last * 0.1;
            high = Math.round(high * 100.0) / 100.0;

            // 根据当前不同的标准BMI区间
            Double[] bmiQujian;
            bmiQujian = Arrays.stream(BmiEnum.values()).filter(var -> stand[0] == var.getStandardIndex()).findFirst().orElse(BmiEnum.CHINASTAND).getDoubles();

            resultMap.put("xAxisDate", xAxisDate);
            resultMap.put("yAxisStandard", yAxisStandard);
            resultMap.put("yAxisTrue", yAxisTrue);
            resultMap.put("yAxisBim", yAxisBim);
            resultMap.put("nowWeight", yAxisTrue.get(yAxisTrue.size() - 1));   // 当前体重
            resultMap.put("nowBmi", yAxisBim.get(yAxisBim.size() - 1));   // 当前BMI
            resultMap.put("nowStandardRange", new Double[]{low, high}); // 标准体重区间
            resultMap.put("nowBmiRange", bmiQujian); // BIM区间
        }

        return ResultHttp.builder().code(1).result(resultMap).build();
    }

    @ApiOperation(value = "查询用户身体参数修改记录", notes = "查询用户身体参数修改记录")
    @GetMapping("/query/update/time")
    @TokenModel
    public ResultHttp getAllUpdateTime(HttpServletRequest request, AnnotationUser annotationUser) {
        List<String> times = bodyArgsService.findUpdateTimeById(annotationUser.getUId()).stream().map(var -> {
            Instant instant = var.toInstant();
            return formatterTime.format(instant.atZone(ZoneId.systemDefault()).toLocalDateTime());
        }).collect(Collectors.toList());
        return ResultHttp.builder().code(1).result(times).build();
    }


}
