package com.zcy.cn.service;

import com.zcy.cn.bean.Degree;
import com.zcy.cn.vo.AnnotationUser;

import java.util.Date;
import java.util.List;

public interface DegreeService {

    // 查询某个用户某天数据
    Degree queryBySomeDay(AnnotationUser annotationUser, Date date);

    Degree saveOuUpdate(Degree degree);

    List<Degree> findCurrentWeek(AnnotationUser annotationUser);


}
