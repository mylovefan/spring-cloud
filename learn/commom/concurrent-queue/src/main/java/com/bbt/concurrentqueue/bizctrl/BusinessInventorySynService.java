package com.bbt.concurrentqueue.bizctrl;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 18:06
 */
public interface BusinessInventorySynService {

    /**
     * 同步减库存
     *
     * @param businessDecisionService
     * @param businessKey
     * @param id
     * @return 是否成功
     */
    boolean decreaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);

    /**
     * 同步加库存
     *
     * @param businessDecisionService
     * @param businessKey
     * @param id
     * @return
     */
    void increaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);

    /**
     * 更新库存
     *
     * @param businessDecisionService
     * @param businessKey
     * @param id
     * @return
     */
    int updateInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);

    /**
     * 获取库存
     * @param businessDecisionService
     * @param businessKey
     * @param id
     * @return
     */
    int getInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id);
}
