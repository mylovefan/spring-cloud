package com.bbt.concurrentqueue.requestctrl;

import java.util.concurrent.Callable;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/23 17:37
 */
public interface ConcurrentTask<T,V> {

    /**
     * 创建线程
     *
     * @return
     */
    default Callable<V> createThread(){
        return () -> execute();
    }

    /**
     * 执行主体
     * @return
     */
    V execute();

    /**
     * 成功时执行
     * @param successInfo
     * @return
     */
    T executeWhenSuccess(V successInfo);

    /**
     * 队列已满时执行
     * @return
     */
    T executeWhenRejected();

    /**
     * 获取结果异常时执行
     * @return
     */
    T executeWhenException();


}
