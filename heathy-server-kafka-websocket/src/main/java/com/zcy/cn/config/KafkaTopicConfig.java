package com.zcy.cn.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 没有TOPIC：FLOW_TOPIC 则创建
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Topid: FLOW_TOPIC
     * 分区数：2
     * 副本数：1
     *
     * @return
     */
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("FLOW_TOPIC", 2, (short) 1);
    }

}
