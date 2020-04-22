package com.zcy.cn.dao;

import com.zcy.cn.bean.Custom;
import com.zcy.cn.dto.CustomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomDao extends JpaRepository<Custom, Long> {

    @Query(value = "SELECT " +
            "new com.zcy.cn.dto.CustomDTO(sup.name, " +
            "big.smallName, " +
            "big.instructions, " +
            "dish.name, " +
            "big.dbId, " +
            "big.icon, " +
            "cus.cWeight, " +
            "cus.bmiFlag) " +
            "FROM " +
            "SuperDish sup " +
            "LEFT JOIN Custom cus ON cus.sId = sup.sId " +
            "LEFT JOIN DishBig big ON big.dSId = sup.sId " +
            "LEFT JOIN Dish dish ON dish.dbiId = big.dbId " +
            "WHERE " +
            "cus.bmiFlag = :bmiFlag")
    List<CustomDTO> findByCustomByBMI(@Param("bmiFlag") Integer bmiFlag);


}
