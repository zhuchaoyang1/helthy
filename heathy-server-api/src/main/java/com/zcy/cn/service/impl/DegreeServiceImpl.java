package com.zcy.cn.service.impl;

import com.zcy.cn.bean.Degree;
import com.zcy.cn.dao.DegreeDao;
import com.zcy.cn.service.DegreeService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class DegreeServiceImpl implements DegreeService {

    @Autowired
    private DegreeDao degreeDao;

    @Override
    public Degree queryBySomeDay(AnnotationUser annotationUser, Date date) {
        Optional<Degree> option = degreeDao.findAllByuIdAndDates(annotationUser.getUId(), date);
        return option.orElse(null);
    }

    @Override
    public Degree saveOuUpdate(Degree degree) {
        return degreeDao.save(degree);
    }

    @Override
    public List<Degree> findCurrentWeek(AnnotationUser annotationUser) {
        // 获取当周周一周日日期
        LocalDate localDate = LocalDate.now();
        LocalDate beginDate = localDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1L);
        LocalDate lastDate = localDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(7L);

        ZonedDateTime zonedDateBegin = beginDate.atStartOfDay(ZoneId.systemDefault());
        ZonedDateTime zonedDateLast = lastDate.atStartOfDay(ZoneId.systemDefault());

        return degreeDao.findSevenRecord(annotationUser.getUId(), Date.from(zonedDateBegin.toInstant()), Date.from(zonedDateLast.toInstant()));
    }


}
