package com.bbt.mq.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:34
 */
@Component
public class CustomConsumerRegisterImpl implements CustomConsumerRegister {
    private Map<String, CustomConsumer> customConsumerMap = new HashMap<>();

    private Map<String, List<String>> topics = new HashMap<>();

    /**
     * 注册消费者
     * @param customConsumer
     */
    @Override
    public void registerConsumers(CustomConsumer customConsumer) {
        this.customConsumerMap.put(customConsumer.getTopic() + "-" + customConsumer.getTag(), customConsumer);

        List<String> tags = this.topics.get(customConsumer.getTopic());
        if (tags == null) {
            tags = new ArrayList<>();
            this.topics.put(customConsumer.getTopic(), tags);
        }

        tags.add(customConsumer.getTag());
    }

    /**
     * 获取消费者
     * @param topic
     * @param tag
     * @return
     */
    @Override
    public CustomConsumer getConsumer(String topic, String tag) {
        return this.customConsumerMap.get(topic + "-" + tag);
    }

    /**
     * 获取注册的消费topic信息
     * @return
     */
    @Override
    public Map<String, List<String>> getTopics() {
        return topics;
    }
}
