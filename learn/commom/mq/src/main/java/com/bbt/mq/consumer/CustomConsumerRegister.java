package com.bbt.mq.consumer;

import java.util.List;
import java.util.Map;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:31
 */
public interface CustomConsumerRegister {

    /**
     * 注册消费者
     * @param customConsumer
     */
    void registerConsumers(CustomConsumer customConsumer);

    /**
     * 获取消费者
     * @param topic
     * @param tag
     * @return
     */
    CustomConsumer getConsumer(String topic, String tag);

    /**
     * 获取注册的消费topic信息
     * @return
     */
    Map<String, List<String>> getTopics();
}
