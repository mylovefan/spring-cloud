package com.bbt.concurrentqueue.bizctrl;


/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/28 11:08
 */
public abstract class AbstractBusinessInventorySynService implements BusinessInventorySynService {

    @Override
    public boolean decreaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        Integer inventory = getInventory(businessDecisionService, businessKey, id);
        if (inventory <= 0) {
            return false;
        }
        Integer inventoryAfter = decreaseInventoryToCache(businessKey, id);
        if (inventoryAfter == null || inventoryAfter < 0) {
            return false;
        }

        return true;
    }

    @Override
    public void increaseInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        increaseInventoryToCache(businessKey, id);
    }

    @Override
    public int updateInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        Integer inventory = businessDecisionService.queryInventory(id);
        setInventoryToCache(businessKey, id, inventory);
        return inventory;
    }

    @Override
    public int getInventory(BusinessDecisionService businessDecisionService, String businessKey, Object id) {
        Integer inventory = getInventoryFromCache(businessKey, id);
        if (inventory == null || inventory < 0) {
            synchronized (businessDecisionService.getSynObj(id)) {
                inventory = getInventoryFromCache(businessKey, id);
                if (inventory == null || inventory < 0) {
                    inventory = updateInventory(businessDecisionService, businessKey, id);
                }
            }
        }
        return inventory;
    }

    /**
     * 从缓存中获取剩余库存
     * @param businessKey
     * @param id
     * @return
     */
    public abstract Integer getInventoryFromCache(String businessKey, Object id);

    /**
     * 更新缓存中的库存
     * @param businessKey
     * @param id
     * @param inventory
     */
    public abstract void setInventoryToCache(String businessKey, Object id, Integer inventory);

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @return
     */
    public abstract Integer decreaseInventoryToCache(String businessKey, Object id);

    /**
     * 缓存中的库存量加一
     * @param businessKey
     * @param id
     * @return
     */
    public abstract void increaseInventoryToCache(String businessKey, Object id);
}
