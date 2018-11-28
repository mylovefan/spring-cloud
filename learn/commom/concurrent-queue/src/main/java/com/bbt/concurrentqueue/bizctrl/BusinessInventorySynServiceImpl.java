package com.bbt.concurrentqueue.bizctrl;


import com.bbt.concurrentqueue.cache.BusinessCacheInventorySynService;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/28 11:44
 */
@Service
public class BusinessInventorySynServiceImpl extends AbstractBusinessInventorySynService {

    @Autowired
    private BusinessCacheInventorySynService businessInventorySynService;
    
    @Override
    public Integer getInventoryFromCache(String businessKey, Object id) {
        Result<Integer> result = businessInventorySynService.getInventory(businessKey, id);
        if (result.getRet() != ResultCode.SUCCESS) {
            return null;
        }
        return result.getModel();
    }

    @Override
    public void setInventoryToCache(String businessKey, Object id, Integer inventory) {
        businessInventorySynService.setInventory(businessKey, id, inventory);
    }

    @Override
    public Integer decreaseInventoryToCache(String businessKey, Object id) {
        Result<Integer> result = businessInventorySynService.decreaseInventory(businessKey, id);
        if (result.getRet() != ResultCode.SUCCESS) {
            return null;
        }
        return result.getModel();
    }

    @Override
    public void increaseInventoryToCache(String businessKey, Object id) {
        businessInventorySynService.increaseInventory(businessKey, id);
    }
}
