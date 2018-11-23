package com.bbt.concurrentqueue.requestctrl;


import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/23 17:46
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractBaseConcurrentTask<T extends Result, V> implements ConcurrentTask<T, V> {

    @SuppressWarnings("unchecked")
    @Override
    public T executeWhenRejected() {
        Result rtn = new Result();
        rtn.setRet(ResultCode.QUEUE_FAILED);
        rtn.setMsg(ResultCode.get(rtn.getRet()));
        return (T) rtn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T executeWhenException() {
        Result rtn = new Result();
        rtn.setRet(ResultCode.QUEUE_FAILED);
        rtn.setMsg(ResultCode.get(rtn.getRet()));
        return (T) rtn;
    }

}
