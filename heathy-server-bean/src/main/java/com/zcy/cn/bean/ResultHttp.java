package com.zcy.cn.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultHttp {

    private Integer code;   // -1 业务失败   0 业务成功

    private Object result;  // 业务返回的数据

}
