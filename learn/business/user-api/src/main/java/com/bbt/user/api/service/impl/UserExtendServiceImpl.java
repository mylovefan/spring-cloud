package com.bbt.user.api.service.impl;

import com.bbt.concurrentqueue.bizctrl.annotation.BusinessInventoryCtrl;
import com.bbt.concurrentqueue.constants.BusinessKey;
import com.bbt.framework.web.Result;
import com.bbt.user.api.service.UserExtendService;
import com.bbt.user.dto.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:46
 */
@Service
public class UserExtendServiceImpl implements UserExtendService {


    @Override
    @BusinessInventoryCtrl(idParamIndex = 0, businessKey = BusinessKey.USER_CREATE_ORDER, using = UserBusinessDecisionServiceImpl.class, isLock = true)
    public Result<OrderDTO> createOrder(Long id) {
        Result<OrderDTO> result = new Result<OrderDTO>();
        OrderDTO dto = new OrderDTO();
        dto.setId(222L);
        result.setModel(dto);
        return result;
    }
}
