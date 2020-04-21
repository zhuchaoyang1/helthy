package com.zcy.cn.util;

import com.zcy.cn.bean.BodyArgs;
import org.springframework.stereotype.Component;

/**
 * 关于标准体重计算
 */
@Component
public class StandardWeightUtil {

    public double caculateStandardWeight(BodyArgs bodyArgs) {
        switch (bodyArgs.getStandard()) {
            case 2: {
                // WHO
                if (bodyArgs.getGender()) {
                    // true 女 (身高cm－70)×60﹪=标准体重
                    return Math.round((bodyArgs.getHeight() - 70) * 0.6 * 100) / 100.0;
                } else {
                    // false 男 (身高cm－80)×70﹪=标准体重
                    return Math.round((bodyArgs.getHeight() - 80) * 0.7 * 100) / 100.0;
                }
            }
            case 1:
            case 0: {
                // 亚洲标准
                // [身高（cm）-100]×0.9
                return Math.round((bodyArgs.getHeight() - 100) * 0.9 * 100.0) / 100.0;
            }
        }
        return 0.0;
    }

}
