package com.bbt.mq.service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:47
 */
public interface FollowTransactionRecordService {
	
	/**
	 * 判断MQ消息是否被成功消费
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 * @return
	 */
	boolean isExist(String topic, Long transationId);
	
	/**
	 * 消息被消费成功，保存一条记录
	 * 
	 * @param topic MQ消息的topic
	 * @param transationId 事务id
	 */
	void consumptionSuccessful(String topic, Long transationId);
	
	/**
	 * 删除过期数据
	 * 改为定时任务执行,因为每次消费都去执行,增加数据库的压力
	 */
	void deleteExpiredRecords();
	
}
