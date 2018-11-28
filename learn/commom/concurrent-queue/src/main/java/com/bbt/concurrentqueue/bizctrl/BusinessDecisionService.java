package com.bbt.concurrentqueue.bizctrl;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:53
 */
public interface BusinessDecisionService<T> {

    /**
     * 查询抢购/秒杀类型业务剩余库存量
     *
     * @retur
     */
    Integer queryInventory(Object id);

    /**
     * 获取同步对象
     * @param id
     * @return
     */
    Object getSynObj(Object id);

    /**
     * 已抢完
     *
     * @return
     */
    T sellOut();

    /**
     * 失败
     * @return
     */
    T fail(BusinessExecuteException e);
}
