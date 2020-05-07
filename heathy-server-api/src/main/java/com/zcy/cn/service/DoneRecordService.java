package com.zcy.cn.service;

import com.zcy.cn.bean.DoneRecord;
import com.zcy.cn.vo.AnnotationUser;
import com.zcy.cn.vo.DoneRecordVO;

import java.util.List;

public interface DoneRecordService {

    List<DoneRecord> save(List<DoneRecord> doneRecord);

    /**
     * 根据日期，Uid获取原始数据库中数据
     *
     * @param annotationUser
     * @param weekIndex
     * @return
     */
    List<DoneRecord> queryByDateAndUid(AnnotationUser annotationUser, Integer weekIndex);

    /**
     * 根据获取的数据计算每一块的占比  用于前端显示饼状图
     *
     * @return
     */
    List<DoneRecordVO> caculateEachDondRecordPercent(AnnotationUser annotationUser, Integer weekIndex);
}
