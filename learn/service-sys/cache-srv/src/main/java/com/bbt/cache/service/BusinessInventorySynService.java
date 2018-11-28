package com.bbt.cache.service;

/**
 * 秒杀库存同步缓存服务接口
 *
 */
public interface BusinessInventorySynService {

    /**
     * 从缓存中获取剩余库存
     * @param businessKey
     * @param id
     * @return
     */
    Integer getInventory(String businessKey, Object id);

    /**
     * 更新缓存中的库存
     * @param businessKey
     * @param id
     * @param inventory
     */
    void setInventory(String businessKey, Object id, Integer inventory);

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @return
     */
    Integer decreaseInventory(String businessKey, Object id);

    /**
     * 缓存中的库存量加一
     * @param businessKey
     * @param id
     * @return
     */
    void increaseInventory(String businessKey, Object id);
    
    /**
     * 删除缓存中的库存key
     * 
     * @param businessKey
     * @param id
     */
    void clearInventory(String businessKey, Object id);
}
