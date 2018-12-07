package com.bbt.mq.consumer;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:29
 */
public abstract class CustomConsumer {
    private String topic;
    private String tag;

    public CustomConsumer(String topic, String tag) {
        this.topic = topic;
        this.tag = tag;
    }



    public abstract void consumeMessage(Object message, long storeTimestamp);

    public String getTopic() {
        return topic;
    }

    public String getTag() {
        return tag;
    }
}
