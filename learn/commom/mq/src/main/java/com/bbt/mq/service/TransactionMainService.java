package com.bbt.mq.service;

/**
 * @Description: 补偿性事务主逻辑接口
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:06
 */
public interface TransactionMainService <R>{

    /**
     * 通知其它模块进行补偿性事务处理
     */
    void sendNotice(Long relateId);

    /**
     * 补偿成功回调
     *
     * @param reply
     */
    void receiveCallback(R reply, long storeTimestamp);

    /**
     * 检查需要补偿的数据
     *
     * @return
     */
    void check(Long count);

    /**
     * 获取当前类注解CompensatingTransactionMain的Topic的属性
     *
     */
    String getTopic();

    /**
     * 执行接收到的回复消息
     *
     */
    void executeCallback(R reply);
}
