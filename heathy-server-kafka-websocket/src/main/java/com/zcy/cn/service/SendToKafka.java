package com.zcy.cn.service;

import com.zcy.cn.contanst.Contanst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class SendToKafka {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendToTopic(String topic, String jsonMsg) {
        kafkaTemplate.send(Contanst._FLOWTOPIC, jsonMsg);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, jsonMsg);
        // Kafka消息都是异步方式， 下为异步回调
        future.addCallback(success -> {
                    String sendTopicName = success.getProducerRecord().topic();
                    Integer partion = success.getProducerRecord().partition();
                    Long offset = success.getRecordMetadata().offset();
                    log.info("\n消息发送成功：\nTopName：{}、Partition：{}、Offset：{}", sendTopicName, partion, offset);
                },
                fail -> log.error("\n发送消息失败，失败原因：\n {}", fail.getMessage()));
    }
}
