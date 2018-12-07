package com.bbt.mq.service;

/**
 * 补偿性事务从逻辑接口
 *
 * @author zhangrc
 * @date 2018/11/30
 */
public interface TransactionFollowService<N, R>{

    /**
     * 通知其它模块进行补偿性事务处理
     *
     * @param notification
     */
    void receiveNotice(N notification, long storeTimestamp);

    /**
     * 补偿成功回调
     *
     */
    void sendCallback(N notification);
    
    
    /**
     * 执行接收到的消息
     * 
     * @param notification 接收的消息
     */
    void executeNotice(N notification);

}
