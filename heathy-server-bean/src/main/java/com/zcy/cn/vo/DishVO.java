package com.zcy.cn.vo;

import com.zcy.cn.bean.Dish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * 菜品
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishVO {

    private Long dbiId;

    private String name;

    public Dish cretaeDish() {
        Dish dish = new Dish();
        BeanUtils.copyProperties(this, dish);
        return dish;
    }

}
