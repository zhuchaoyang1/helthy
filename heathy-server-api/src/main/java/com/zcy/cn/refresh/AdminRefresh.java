package com.zcy.cn.refresh;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "config.admin")
public class AdminRefresh {

    private String uName;

    private String uPwd;

}
