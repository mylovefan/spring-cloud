package com.bbt.cache.service.impl;

import com.bbt.cache.constants.KeyConstant;
import com.bbt.cache.service.BusinessInventorySynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 秒杀库存同步缓存服务接口实现类
 *
 */
@Service
public class BusinessInventorySynServiceImpl implements BusinessInventorySynService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Integer getInventory(String businessKey, Object id) {
        return InventoryCacheManager.getInventoryFromCache(stringRedisTemplate, KeyConstant.REDIS_KEY_BUSINESS_INVENTORY_SYN, businessKey, id);
    }

    @Override
    public void setInventory(String businessKey, Object id, Integer inventory) {
        InventoryCacheManager.setInventoryToCache(stringRedisTemplate, KeyConstant.REDIS_KEY_BUSINESS_INVENTORY_SYN, businessKey, id, inventory);
    }

    @Override
    public Integer decreaseInventory(String businessKey, Object id) {
        return InventoryCacheManager.decreaseInventoryToCache(stringRedisTemplate, KeyConstant.REDIS_KEY_BUSINESS_INVENTORY_SYN, businessKey, id);
    }

    @Override
    public void increaseInventory(String businessKey, Object id) {
        InventoryCacheManager.increaseInventoryToCache(stringRedisTemplate, KeyConstant.REDIS_KEY_BUSINESS_INVENTORY_SYN, businessKey, id);
    }
    
    @Override
    public void clearInventory(String businessKey, Object id) {
        InventoryCacheManager.clearInventoryFromCache(stringRedisTemplate, KeyConstant.REDIS_KEY_BUSINESS_INVENTORY_SYN, businessKey, id);
    }
}
