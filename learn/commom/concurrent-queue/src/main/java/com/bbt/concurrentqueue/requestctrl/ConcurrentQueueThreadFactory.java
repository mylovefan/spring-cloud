package com.bbt.concurrentqueue.requestctrl;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/23 17:32
 */
public class ConcurrentQueueThreadFactory implements ThreadFactory{

    private final AtomicLong threadIndex = new AtomicLong(0);
    private final String threadNamePrefix;

    public ConcurrentQueueThreadFactory(final String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadNamePrefix + this.threadIndex.incrementAndGet());
    }
}
