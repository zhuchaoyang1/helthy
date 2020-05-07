package com.zcy.cn.dao;

import com.zcy.cn.bean.PrivateLetter;
import com.zcy.cn.vo.PrivateLetterVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrivateLetterDao extends JpaRepository<PrivateLetter, Long> {

    /**
     * 查询关于我未读的信息条数
     *
     * @return
     */
    @Query(value = "SELECT COUNT(pl) FROM PrivateLetter pl WHERE pl.uIdByMe = :meId AND pl.hasLooked = false")
    Integer queryByNeverReadCount(@Param(value = "meId") Long meId);

    /**
     * 查询关于我未读的信息条数
     *
     * @return
     */
    @Query(value = "SELECT new com.zcy.cn.vo.PrivateLetterVO(pl,u) FROM PrivateLetter pl LEFT JOIN Users u ON u.uId = pl.uIdByOther WHERE pl.uIdByMe = :meId AND pl.hasLooked = false ORDER BY pl.pLId DESC")
    List<PrivateLetterVO> queryByNeverRead(@Param(value = "meId") Long meId, Pageable pageable);

}
