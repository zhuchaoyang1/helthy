package com.zcy.cn.service.impl;

import com.zcy.cn.bean.DoneRecord;
import com.zcy.cn.dao.DoneRecordDao;
import com.zcy.cn.service.DoneRecordService;
import com.zcy.cn.util.TimeUtil;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.DoneRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class DoneRecordServiceImpl implements DoneRecordService {

    @Autowired
    private DoneRecordDao doneRecordDao;

    @Override
    public List<DoneRecord> save(List<DoneRecord> doneRecords) {
        return doneRecordDao.saveAll(doneRecords);
    }

    /**
     * weekIndex ： 0 ~ 7 其中7：表示今天
     *
     * @param annotationUser
     * @param weekIndex
     * @return
     */
    @Override
    public List<DoneRecord> queryByDateAndUid(AnnotationUser annotationUser, Integer weekIndex) {
        if (weekIndex == 7) {
            return doneRecordDao.queryDoneRecordByUidDate(annotationUser.getUId(), new Date());
        }
        Date[] dates = TimeUtil.buildCurrentWeekDate(TimeUtil.buildCurrWeekLocalDate(LocalDate.now()));
        return doneRecordDao.queryDoneRecordByUidDate(annotationUser.getUId(), dates[weekIndex]);
    }

    @Override
    public List<DoneRecordVO> caculateEachDondRecordPercent(AnnotationUser annotationUser, Integer weekIndex) {
        Date[] dates = TimeUtil.buildCurrentWeekDate(TimeUtil.buildCurrWeekLocalDate(LocalDate.now()));
        List<DoneRecordVO> recordFrom;
        if (weekIndex == 7) {
            recordFrom = doneRecordDao.queryDoneRecordByUidDateToVO(annotationUser.getUId(), new Date());
        } else {
            recordFrom = doneRecordDao.queryDoneRecordByUidDateToVO(annotationUser.getUId(), dates[weekIndex]);
        }
        if (CollectionUtils.isEmpty(recordFrom)) {
            return new ArrayList<>();
        }

        // 计算饼图
        int count = recordFrom.size();
        double eachPercent = 1.0 / count;
        recordFrom.forEach(var -> {
            var.setPercent((var.getPercent() / 100 * eachPercent) * 100);
        });
        return recordFrom;
    }
}
