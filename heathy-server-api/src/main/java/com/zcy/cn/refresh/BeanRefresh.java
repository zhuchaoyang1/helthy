package com.zcy.cn.refresh;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 将所有的动态配置Bean整合进来
 * 用于Github配置更新之后，做统一Controller查看配置是否更新
 */
@Data
@Component
public class BeanRefresh {

    @Autowired
    private AdminRefresh adminRefresh;

}
