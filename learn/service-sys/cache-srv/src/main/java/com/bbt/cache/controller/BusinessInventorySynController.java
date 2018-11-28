package com.bbt.cache.controller;

import com.bbt.cache.service.BusinessInventorySynService;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 秒杀库存同步缓存服务控制器
 *
 */
@RestController
@RequestMapping(value = "businessInventorySyn/")
public class BusinessInventorySynController extends BaseController {
	
	@Autowired
	private BusinessInventorySynService businessInventorySynService;
	
	/**
	 * 从缓存中获取剩余库存
	 * @param businessKey
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "inventory", method = RequestMethod.GET)
    Result<Integer> getInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
	     return successGet(businessInventorySynService.getInventory(businessKey, id));
    }

	/**
	 * 更新缓存中的库存
	 * @param businessKey
	 * @param id
	 * @param inventory
	 * @return
	 */
    @RequestMapping(value = "setInventory", method = RequestMethod.PUT)
    Result<?> setInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id, @RequestParam("inventory") Integer inventory) {
        businessInventorySynService.setInventory(businessKey, id, inventory);
        return successCreated();
    }

    /**
     * 缓存中的库存量减一
     * @param businessKey
     * @param id
     * @return
     */
	@RequestMapping(value = "decreaseInventory", method = RequestMethod.PUT)
	Result<Integer> decreaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
        return successCreated(businessInventorySynService.decreaseInventory(businessKey, id));
    }

    /**
	 * 缓存中的库存量加一
	 * 
	 * @param businessKey
	 * @param id
	 */
	@RequestMapping(value = "increaseInventory", method = RequestMethod.PUT)
    Result<?> increaseInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
	    businessInventorySynService.increaseInventory(businessKey, id);
        return successCreated();
    }
	
    /**
     * 删除缓存中的库存key
     * 
     * @param businessKey
     * @param id
     */
    @RequestMapping(value = "clearInventory", method = RequestMethod.PUT)
    Result<?> clearInventory(@RequestParam("businessKey") String businessKey, @RequestParam("id") Object id) {
        businessInventorySynService.clearInventory(businessKey, id);
        return successCreated();
    }
}
