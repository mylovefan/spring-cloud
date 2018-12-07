package com.bbt.mq.message.impl;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.List;

import com.bbt.mq.consumer.CustomConsumer;
import com.bbt.mq.consumer.CustomConsumerRegister;
import com.bbt.mq.exception.NegligibleException;
import com.bbt.mq.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/**
 * 设备报文消费监听
 */
public abstract class AbstractMessageConsumerListener implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(AbstractMessageConsumerListener.class);

    @Autowired
    private CustomConsumerRegister customConsumerRegister;
    
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : msgs) {
            String topic = messageExt.getTopic();
            String tags = messageExt.getTags();
            byte[] body = messageExt.getBody();
            logger.info("Consume Message: {},{},{}", messageExt.getMsgId(), topic, tags);
            Object object = null;
            try {
                object = ByteUtil.byteToObject(body);
                /*
                 *  打印消息对象
                 *  放入try-catch代码块，防止对象转换json报错
                 */
                logger.info("Message content: {},{}", messageExt.getMsgId(), JSONObject.toJSONString(object));
            } catch(InvalidClassException e) {
            	logger.warn("Message type does not match", e);
                continue;
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Failure consumption, later try to consume", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            } catch (JSONException e) {
                // for logger
                logger.warn("Object To JSON String Failure", e);
            }
            
            // 捕捉自定义异常,标识这条消息已经处理,或者不处理
            try {
                if (customConsumerRegister != null) {
                    CustomConsumer customConsumer = customConsumerRegister.getConsumer(topic, tags);
    
                    // 自定义优先
                    if (customConsumer != null) {
                        customConsumer.consumeMessage(object, messageExt.getStoreTimestamp());
                        continue;
                    }
                }
                consumeMessage(topic, tags, object);
            } catch (NegligibleException e) {
                logger.warn("Message has be deal");
            }

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public abstract void consumeMessage(String topic, String tags, Object message);
}
