package com.zcy.cn.vo;

import com.zcy.cn.bean.Custom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 根据BMI几种状态 定制化 主食食用量
 * 瘦、偏瘦、标准呢、偏胖、肥胖、极度肥胖
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomVO {

    private Long cId;

    private Long SId;     // super大类别表外键

    private String sIs;

    private Long isDeleted;
    /**
     * 0：瘦
     * 1：偏瘦
     * 2：标准
     * 3：偏胖
     * 4：肥胖
     * 5：极度肥胖
     */
    private Integer bmiFlag;

    private Double cWeight;

    public Custom createCustom() {
        Custom custom = new Custom();
        BeanUtils.copyProperties(this, custom);
        return custom;
    }

}
