package com.bbt.mq.service.impl;

import com.bbt.mq.Notification;
import com.bbt.mq.Reply;
import com.bbt.mq.TransactionProperties;
import com.bbt.mq.annotation.CompensatingTransactionMain;
import com.bbt.mq.message.MessageProducerService;
import com.bbt.mq.service.TransactionMainService;
import com.bbt.mq.service.TransactionStatusService;
import com.bbt.mq.util.SpringApplicationContextTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:10
 */
@Component
public abstract class AbstractTransactionMainService <N extends Notification, R extends Reply> implements TransactionMainService<R>{

    private static Logger logger = LoggerFactory.getLogger(AbstractTransactionMainService.class);

    @Autowired
    private TransactionStatusService transactionStatusService;

    @Autowired
    private MessageProducerService messageProducerService;

    @Autowired
    private SpringApplicationContextTool springApplicationContextTool;

    @Autowired
    private TransactionProperties transactionProperties;

    /**
     * 获取主事物注解参数
     */
    private CompensatingTransactionMain annotation = this.getClass().getAnnotation(CompensatingTransactionMain.class);

    private byte type = annotation.value();

    private String topic = annotation.topic();

    private String tags = annotation.tags();

    /**
     * 查询需要发送到其他模块的数据
     * @param relateId
     * @return
     */
    public abstract N selectNotification(Long relateId);

    /**
     * 事务成功回调时，需要执行的逻辑
     * 默认为空，需要的话可以Override
     *
     * @param relateId
     * @param reply
     */
    public void afterSuccess(Long relateId, R reply) {
        return;
    }

    @Override
    public void sendNotice(Long relateId) {
        N notification = selectNotification(relateId);

        if (notification == null) {
            throw new IllegalArgumentException("Can't find the notification by relateId: " + relateId);
        }
        Long transactionId = transactionStatusService.save(relateId,type);

        notification.setTransactionId(transactionId);

        messageProducerService.sendMessage(topic, tags, notification);

    }

    @Override
    public void receiveCallback(R reply, long storeTimestamp) {

        if (System.currentTimeMillis() - storeTimestamp >= transactionProperties.getMessageValidTime()) {
            logger.info("回调消息已经失效");
            return;
        }
        // 统一处理事务异常，手动捕捉异常，并且打印错误信息
        StringBuilder locakName = new StringBuilder();
        locakName.append(topic).append("_").append(annotation.tags() + "-reply").append("_").append(reply.getTransactionId());
        // 从事务幂等性保证，表中存在记录，说明消息已经被成功消费，直接返回
        if (transactionStatusService.isSuccess(reply.getTransactionId())) {
            logger.info("回调消息已经被消费");
            return;
        }
        try {
            TransactionMainService<R> proxy = springApplicationContextTool.getProxy(this, TransactionMainService.class);
            proxy.executeCallback(reply);
        } catch (Exception e) {
            logger.error("回调事务执行异常", e);
            // 抛出异常，回滚事务
            throw e;
        } finally {
        }

    }

    @Override
    public void check(Long count) {

    }

    @Override
    public String getTopic() {
        return null;
    }

    @Override
    public void executeCallback(R reply) {
        Long relateId = transactionStatusService.success(reply.getTransactionId());
        if (relateId == null) {
            logger.info("回调消息已消费");
            return;
        }
        afterSuccess(relateId, reply);
    }
}
