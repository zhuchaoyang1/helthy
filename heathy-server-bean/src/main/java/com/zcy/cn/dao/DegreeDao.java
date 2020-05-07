package com.zcy.cn.dao;

import com.zcy.cn.bean.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DegreeDao extends JpaRepository<Degree, Long> {

    Optional<Degree> findAllByuIdAndDates(Long uId, Date dates);

    // 查询当周七天数据
    @Query(value = "SELECT dg FROM Degree dg WHERE dg.uId = :uId AND dg.dates BETWEEN :beginDate AND :lastDate")
    List<Degree> findSevenRecord(@Param("uId") Long uId,
                                 @Param("beginDate") Date beginDate,
                                 @Param("lastDate") Date lastDate);
}
