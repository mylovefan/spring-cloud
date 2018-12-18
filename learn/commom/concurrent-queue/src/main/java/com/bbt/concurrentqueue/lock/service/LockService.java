package com.bbt.concurrentqueue.lock.service;

/**
 * 分布式事务锁接口
 * 
 * 实现同步注入LockService接口
 * <p>
 * 在方法开始执行<code>tryLock<code>获取同步锁
 * <p>
 * 方法执行完成执行<code>unLock<code>释放同步锁
 * 
 * @author zhangrc
 * @date 2018年12月13日
 */
public interface LockService {

    /**
     * 判断锁是否存在 如果存在返回false 如果不存在，生成一个锁，并且返回true
     * 
     * @param lockKey
     *            锁的名称
     * @return
     */
    boolean tryLock(String lockKey);

    /**
     * 判断锁是否存在 如果存在返回false 如果不存在，生成一个锁，并且返回true
     * 
     * @param waitTime
     *            等待时间
     * @param leaseTime
     *            释放时间
     * @param lockKey
     *            锁的名称
     * @return
     */
    boolean tryLock(long waitTime, long leaseTime, String lockKey);

    /**
     * 释放锁
     * 
     * @param lockKey
     *            锁的名称
     */
    void unLock(String lockKey);

}
