package com.bbt.user.api.service;

import com.bbt.framework.web.Result;
import com.bbt.user.dto.OrderDTO;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:47
 */
public interface UserExtendService {

    /**
     * 创建订单
     * @param id
     * @return
     */
    Result<OrderDTO> createOrder(Long id);
}
