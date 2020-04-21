package com.zcy.cn.util;

import com.zcy.cn.bean.BodyArgs;
import com.zcy.cn.enums.BmiEnum;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class BmiUtil {

    // 计算BMI指数
    public double caculateBodyByBmi(BodyArgs bodyArgs) {
        return Math.round(bodyArgs.getWeight() / Math.pow((bodyArgs.getHeight() / 100.0), 2) * 100) / 100.0;
    }

    // 根据BMI以及标准计算个人身体状况
    public String caculateText(int standardIndex, double bmi) {
        String[] bodyStatus = {"瘦", "偏瘦", "标准", "偏胖", "肥胖", "极度肥胖"};

        Double[] bmiQujian;
        bmiQujian = Arrays.stream(BmiEnum.values()).filter(var -> standardIndex == var.getStandardIndex()).findFirst().orElse(BmiEnum.CHINASTAND).getDoubles();

        // 为了方便计算， double转LinkedList
        LinkedList<Double> handleQujian = new LinkedList<>(Arrays.asList(bmiQujian));
        handleQujian.addFirst(0.0);
        handleQujian.add(Double.MAX_VALUE);     // 避免越界
        for (int i = 0; i < handleQujian.size(); i++) {
            if (handleQujian.get(i) <= bmi && bmi <= handleQujian.get(i + 1)) {
                return bodyStatus[i];
            }
        }
        return "";
    }

}
