package com.zcy.cn.dao;

import com.zcy.cn.bean.Custom;
import com.zcy.cn.dto.AdminCustomChangeDTO;
import com.zcy.cn.dto.CustomDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomDao extends JpaRepository<Custom, Long> {

    @Query(value = "SELECT " +
            "new com.zcy.cn.dto.CustomDTO(sup.name, " +
            "sup.sId, " +
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

    @Query(value = "SELECT " +
            "new com.zcy.cn.dto.AdminCustomChangeDTO(sup.sId, " +
            "sup.name, " +
            "cus.cWeight, " +
            "cus.bmiFlag) " +
            "FROM " +
            "Custom cus " +
            "LEFT JOIN SuperDish sup ON cus.sId = sup.sId " +
            "WHERE " +
            "cus.bmiFlag = :bmiFlag")
    List<AdminCustomChangeDTO> adminBmiWeight(@Param("bmiFlag") Integer bmiFlag);

    @Modifying
    @Query("UPDATE Custom c SET c.cWeight=:cWeight WHERE c.sId=:sId AND c.bmiFlag=:bmiFlag")
    void update(@Param("cWeight") Double cWeight, @Param("sId") Long sId, @Param("bmiFlag") Integer bmiFlag);
}
