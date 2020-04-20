package com.zcy.cn.vo;

import com.zcy.cn.bean.DishBig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishBigVO {

    private String bigName; // 所属大类别名：主食、蔬菜、水果、畜禽肉类、鱼虾类、奶制品、大豆及坚果类、油、盐

    private String smallName;   // 大类中的小类：主食-->谷物、薯类等

    private String icon;    // 记录Icon名

    private String instructions;

    public DishBig createDisBig() {
        DishBig dishBig = new DishBig();
        BeanUtils.copyProperties(this, dishBig);
        return dishBig;
    }

}
