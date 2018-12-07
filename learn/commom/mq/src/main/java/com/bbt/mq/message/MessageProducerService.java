package com.bbt.mq.message;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:22
 */
public interface MessageProducerService {

    /**
     * 消息发送
     *
     * @param topic 主题
     * @param tags 标签
     * @param message 消息内容
     */
    void sendMessage(String topic, String tags, Object message);
}
