package com.bbt.concurrentqueue.lock.service;

import java.util.concurrent.TimeUnit;

import com.bbt.concurrentqueue.lock.properties.RedissonProperties;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 分布式事务锁接口实现抽象类
 *
 */
public abstract class AbstractLockService implements LockService {

    private static Logger logger = LoggerFactory.getLogger(AbstractLockService.class);

    /**
     * 分布式事务同步锁前缀
     */
    public static final String DISTRIBUTED_SYNCHRONIZATION_LOCK = "SYN_LOCK_";

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Autowired
    private RedissonProperties properties;

    /**
     * 判断锁是否存在 如果存在返回false 如果不存在，生成一个锁，并且返回true
     * 
     * @param lockKey
     *            锁的名称
     * @return
     */
    @Override
    public boolean tryLock(String lockKey) {
        RedissonProperties.Lock lock = properties.getLock();
        return tryLock(lock.getWaitTime(), lock.getLeaseTime(), lockKey);
    }

    /**
     * 释放锁
     * 
     * @param lockKey
     *            锁的名称
     */
    @Override
    public void unLock(String lockKey) {
        RLock rLock = getLock(lockKey);
        try {
            rLock.unlock();
        } catch (IllegalMonitorStateException e) {
            logger.error("释放锁失败", e);
        }
    }
    
    @Override
    public boolean tryLock(long waitTime, long leaseTime, String lockKey) {
        boolean rtn = false;
        RLock rLock = getLock(lockKey);
        try {
            rtn = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("获取锁失败", e);
        }
        return rtn;
    }
    
    /**
     * 获取锁
     * 
     * @param lockKey
     *            锁的名称
     * @return
     */
    private RLock getLock(String lockKey) {
        String key = DISTRIBUTED_SYNCHRONIZATION_LOCK.concat(lockKey);
        return redissonClient.getLock(key);
    }
}
