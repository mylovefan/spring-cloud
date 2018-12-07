package com.bbt.mq.service;

import com.bbt.mq.bo.TransactionRecordBO;

import java.util.List;


/**
 * @Description: 事务状态服务类
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:16
 */
public interface TransactionStatusService {

    /**
     * 保存事务状态信息
     * @param relateId
     * @param type
     * @return
     */
    Long save(Long relateId, byte type);

    /**
     * 事务处理成功
     *
     * @param transactionId
     * @return
     */
    Long success(Long transactionId);

    /**
     * 查出指定类型的所有未处理事务
     *
     * @param type
     * @return
     */
    List<TransactionRecordBO> selectNotDoneList(byte type, Long exectotalCount);
    
    /**
     * 更新事务记录的执行次数
     * 
     * @param transactionId	事务记录id
     * @param times 当前执行次数
     */
    void updateTimes(Long transactionId, Long times);
    
    /**
     * 删除过期数据
     * 改为定时任务执行,因为每次消费都去执行,增加数据库的压力
     */
    void deleteExpiredRecords();
    
    /**
     * 事务是否执行成功
     * @param transactionId 事务id
     * @return
     */
    Boolean isSuccess(Long transactionId);
}
