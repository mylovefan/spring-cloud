package com.bbt.concurrentqueue.requestctrl;

import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.*;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/23 17:23
 */
public class ConcurrentTaskExecutor implements InitializingBean {


    /**
     * 核心线程数，指保留的线程池大小
     */
    private int corePoolSize = 2;

    /**
     * 指的是线程池的最大数
     */
    private int maximumPoolSize = 5;

    /**
     * 指的是空闲线程结束的超时时间(秒)
     */
    private long keepAliveTime = 60;

    /**
     * 队列大小
     */
    private int queueCount = 2;

    private BlockingQueue<Runnable> workQueue;

    private ExecutorService executorService;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }

    public Object execute(ConcurrentTask concurrentTask){
        try {

            Callable thread = concurrentTask.createThread();
            Future<Object> future = executorService.submit(thread);
            Object result = future.get();
            return concurrentTask.executeWhenSuccess(result);

        } catch (RejectedExecutionException e) {
            return concurrentTask.executeWhenRejected();
        } catch (InterruptedException | ExecutionException e) {
            return concurrentTask.executeWhenException();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        workQueue = new LinkedBlockingQueue<>(queueCount);
        executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, new ConcurrentQueueThreadFactory("ConcurrentQueueThread_"));


    }


}
