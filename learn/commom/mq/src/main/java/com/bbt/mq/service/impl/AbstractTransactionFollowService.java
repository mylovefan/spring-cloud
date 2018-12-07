package com.bbt.mq.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

import com.bbt.mq.Notification;
import com.bbt.mq.Reply;
import com.bbt.mq.TransactionProperties;
import com.bbt.mq.annotation.CompensatingTransactionFollow;
import com.bbt.mq.exception.NegligibleException;
import com.bbt.mq.message.MessageProducerService;
import com.bbt.mq.service.FollowTransactionRecordService;
import com.bbt.mq.service.TransactionFollowService;
import com.bbt.mq.util.SpringApplicationContextTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:45
 */
@Component
public abstract class AbstractTransactionFollowService<N extends Notification, R extends Reply> implements TransactionFollowService<N, R> {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractTransactionFollowService.class);
	
    CompensatingTransactionFollow annotation = this.getClass().getAnnotation(CompensatingTransactionFollow.class);
    
    @Autowired
    private MessageProducerService messageProducerService;
    
    private String topic = annotation.topic();

    private String tags = annotation.tags() + "-reply";

    
    @Autowired
    private FollowTransactionRecordService followTransactionRecordService;

    @Autowired
    private TransactionProperties transactionProperties;
    
    @Autowired
    private SpringApplicationContextTool springApplicationContextTool;
    
    @SuppressWarnings("unchecked")
    @Override
    public void receiveNotice(N notification, long storeTimestamp) {
        if (System.currentTimeMillis() - storeTimestamp >= transactionProperties.getMessageValidTime()) {
            logger.info("消息已经失效");
            return;
        }
    	// 统一处理事务异常，手动捕捉异常，并且打印错误信息
    	StringBuilder locakName = new StringBuilder();
		locakName.append(topic).append("_").append(annotation.tags()).append("_").append(notification.getTransactionId());
		// 从事务幂等性保证，表中存在记录，说明消息已经被成功消费，直接返回
		if (followTransactionRecordService.isExist(topic, notification.getTransactionId())) {
			logger.info("消息已经被消费");
            sendCallback(notification);
			return;
		}

    	try {
    	    TransactionFollowService<N, R> proxy = springApplicationContextTool.getProxy(this, TransactionFollowService.class);
    	    proxy.executeNotice(notification);
    	} catch (NegligibleException e) {
    	    logger.warn("从事务记录重复 {}", e.getMessage());
        } catch (Exception e) {
            logger.error("事务执行异常", e);
            // 抛出异常，回滚事务
            throw new RuntimeException(e);
    	} finally {

        }
    }

    @Override
    public void sendCallback(N notification) {
        R reply = getReply(notification);
        if (reply == null) {
            return;
        }
        reply.setTransactionId(notification.getTransactionId());
        messageProducerService.sendMessage(topic, tags, reply);
    }

    /**
     * 接收到补偿性事务通知时需要处理的逻辑
     *
     * @param notification
     */
    public abstract void execute(N notification);

    @Override
    public void executeNotice(N notification) {
        execute(notification);
        try {
            // 如果消息被成功消费，保存一条消费记录
            followTransactionRecordService.consumptionSuccessful(topic, notification.getTransactionId());
        } catch (DuplicateKeyException e) {
            throw new NegligibleException(e);
        }
        // 只有事务全部执行成功，才会发送回复消息
        sendCallback(notification);
    }
    
    /**
     * 默认为返回一个Reply空对象
     * 需要的话可以Override
     *
     * @param notification
     */
    @SuppressWarnings("unchecked")
	public R getReply(N notification){
    	try {
			return ((Class<R>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("实例化泛型异常", e);
		}
    	return null;
    }
}
