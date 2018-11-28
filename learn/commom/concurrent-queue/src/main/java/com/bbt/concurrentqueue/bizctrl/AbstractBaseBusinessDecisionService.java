package com.bbt.concurrentqueue.bizctrl;

import com.bbt.concurrentqueue.AbstractBusinessDecisionService;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 18:14
 */
@Component
public abstract class AbstractBaseBusinessDecisionService  extends AbstractBusinessDecisionService<Result> {

    @Override
    public Result fail(BusinessExecuteException e) {
        Result rs = new Result();
        if (e.getRet() != 0) {
            rs.setRet(e.getRet());
            rs.setMsg(e.getMsg());
        } else {
            rs.setRet(ResultCode.QUEUE_FAILED);
            rs.setMsg(ResultCode.get(rs.getRet()));
        }
        return rs;
    }
}
