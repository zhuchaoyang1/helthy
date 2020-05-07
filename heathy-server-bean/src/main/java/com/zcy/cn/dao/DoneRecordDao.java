package com.zcy.cn.dao;

import com.zcy.cn.bean.DoneRecord;
import com.zcy.cn.vo.DoneRecordVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DoneRecordDao extends JpaRepository<DoneRecord, Long> {

    @Query(value = "SELECT dr FROM DoneRecord dr WHERE dr.uId = :uId AND dr.dates = :dates")
    List<DoneRecord> queryDoneRecordByUidDate(@Param("uId") Long uId, @Param("dates") Date dates);

    @Query(value = "SELECT new com.zcy.cn.vo.DoneRecordVO(dr.percent,sd.name) FROM DoneRecord  dr LEFT JOIN SuperDish sd ON dr.sId = sd.sId WHERE dr.uId = :uId AND dr.dates = :dates")
    List<DoneRecordVO> queryDoneRecordByUidDateToVO(@Param("uId") Long uId, @Param("dates") Date dates);

}
