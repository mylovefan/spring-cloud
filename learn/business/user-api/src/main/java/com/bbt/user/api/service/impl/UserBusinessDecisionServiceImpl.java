package com.bbt.user.api.service.impl;

import com.bbt.concurrentqueue.bizctrl.AbstractBaseBusinessDecisionService;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 18:13
 */
@Service
public class UserBusinessDecisionServiceImpl extends AbstractBaseBusinessDecisionService {

    @Override
    public Integer queryInventory(Object id) {
        return 1;
    }

    @Override
    public Result sellOut() {
        Result rs = new Result();
        rs.setRet(ResultCode.QUEUE_FAILED);
        rs.setMsg(ResultCode.get(rs.getRet()));
        return rs;
    }
}
