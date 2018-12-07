package com.bbt.mq.message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bbt.mq.consumer.CustomConsumerRegister;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
/**
 * MQ消息者工厂ß
 *
 * @author zhangrc
 * @date 2018/12/4
 */
public class MQConsumerFactory {
    
    private Map<String, String> topicsTags;
    
    @Autowired
    private DefaultMQPushConsumer consumer;

    @Autowired
    private MessageListenerConcurrently messageListenerConcurrently;

    @Autowired
    private CustomConsumerRegister customConsumerRegister;

    public void createDeviceDatagramConsumer() throws MQClientException {
        if (topicsTags != null && !topicsTags.isEmpty()) {
            Iterator<Map.Entry<String, String>> iterator = topicsTags.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> topicTags = iterator.next();
                consumer.subscribe(topicTags.getKey(), topicTags.getValue());
            }
        }
        subscribeCustomConsumer();
        consumer.registerMessageListener(messageListenerConcurrently);
        consumer.start();
    }

    /**
     * 订阅自定义消费者涉及到的topic
     * @throws MQClientException
     */
    private void subscribeCustomConsumer() throws MQClientException {
        if (customConsumerRegister != null) {
            Map<String, List<String>> topics = customConsumerRegister.getTopics();
            Iterator<Map.Entry<String, List<String>>> topicIterator = topics.entrySet().iterator();
            while (topicIterator.hasNext()) {
                Map.Entry<String, List<String>> topic = topicIterator.next();
                List<String> tags = topic.getValue();
                
                String subExpression = tags.get(0);
                for (int i = 1; i < tags.size(); i++) {
                    subExpression += "|| " + tags.get(i);
                }
                
                String topicKey = topic.getKey();
                
                if (topicsTags != null && !topicsTags.isEmpty() && topicsTags.containsKey(topicKey)) {
                	subExpression +=  "|| " +  topicsTags.get(topicKey);
                }
                consumer.subscribe(topicKey, subExpression);
            }
        }
    }

    public void shutdown() {
        consumer.shutdown();
    }

    public Map<String, String> getTopicsTags() {
        return topicsTags;
    }
    
    public void setTopicsTags(Map<String, String> topicsTags) {
        this.topicsTags = topicsTags;
    }
}
