package com.zcy.cn.vo;

import lombok.Data;

import java.util.Date;

/**
 * 记录每天完成任务情况
 */
@Data
public class DoneRecordVO {

    private Long uId;

    private Long sId;

    private Double percent;

    private Date dates;

    private String name;

    public DoneRecordVO() {
    }

    public DoneRecordVO(Double percent, String name) {
        this.percent = percent;
        this.name = name;
    }


}
