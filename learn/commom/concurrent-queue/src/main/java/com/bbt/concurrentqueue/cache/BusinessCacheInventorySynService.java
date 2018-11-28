package com.bbt.concurrentqueue.cache;

import com.bbt.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(value = "cache-srv", path = "businessInventorySyn/")
public interface BusinessCacheInventorySynService {
	
    /**
     * 从缓存中获取剩余库存
     * @param businessKey
     * @param id
     * @return
     */
    @RequestMapping(value = "inventory", method = RequestMethod.GET)
    Result<Integer> getInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id);

    /**
     * 更新缓存中的库存
     * @param businessKey
     * @param id
     * @param inventory
     * @return
     */
    @RequestMapping(value = "setInventory", method = RequestMethod.PUT)
    Result<?> setInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id, @RequestParam("inventory") Integer inventory);

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @retur
     */
    @RequestMapping(value = "decreaseInventory", method = RequestMethod.PUT)
    Result<Integer> decreaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id);

    /**
     * 缓存中的库存量加一
     * 
     * @param businessKey
     * @param id
     */
    @RequestMapping(value = "increaseInventory", method = RequestMethod.PUT)
    Result<?> increaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id);
    
    /**
     * 删除缓存中的库存key
     * 
     * @param businessKey
     * @param id
     */
    @RequestMapping(value = "clearInventory", method = RequestMethod.PUT)
    Result<?> clearInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id);
}
