package com.bbt.mq.service.impl;

import java.util.Calendar;
import java.util.Date;

import com.bbt.mq.TransactionProperties;
import com.bbt.mq.domain.FollowTransactionRecordDO;
import com.bbt.mq.domain.FollowTransactionRecordDOExample;
import com.bbt.mq.mapper.FollowTransactionRecordDOMapper;
import com.bbt.mq.mapper.extend.FollowTransactionRecordDOExtendMapper;
import com.bbt.mq.service.FollowTransactionRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FollowTransactionRecordServiceImpl implements FollowTransactionRecordService {
    
    private static final Logger log = LoggerFactory.getLogger(FollowTransactionRecordServiceImpl.class);

    @Autowired
    private FollowTransactionRecordServiceImpl followTransactionRecordServiceImpl;
    
    @Autowired
    private FollowTransactionRecordDOMapper followTransactionRecordDOMapper;

    @Autowired
    private FollowTransactionRecordDOExtendMapper followTransactionRecordDOExtendMapper;

    @Autowired
    private TransactionProperties transactionProperties;

    @Override
    public boolean isExist(String topic, Long transationId) {
        FollowTransactionRecordDOExample example = new FollowTransactionRecordDOExample();
        example.createCriteria().andTopicEqualTo(topic).andTransationIdEqualTo(transationId);
        followTransactionRecordDOMapper.countByExample(example);
        return followTransactionRecordDOMapper.countByExample(example) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void consumptionSuccessful(String topic, Long transationId) {
        FollowTransactionRecordDO followTransactionRecordDO = new FollowTransactionRecordDO();
        followTransactionRecordDO.setTransationId(transationId);
        followTransactionRecordDO.setTopic(topic);
        followTransactionRecordDO.setGmtCreate(new Date());
        followTransactionRecordDOMapper.insert(followTransactionRecordDO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteExpiredRecords() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 设置时分秒为0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -transactionProperties.getDeleteRecordTime());

        // 执行错误次数
        int errorCount = 0;
        // 循环删除过期数据
        while (true) {
            if (errorCount >= 3) {
                break;
            }
            /*
             *  根据受影响的行数来判断以及异常次数
             *  是否结束循环
             */
            try {
                int affectedRows = followTransactionRecordServiceImpl.deleteExpiredRecords(calendar.getTime());
                if (affectedRows == 0) {
                    break;
                }
            } catch (Exception e) {
                errorCount++;
                log.error("删除主事务记录异常", e);
            }
        }
    }
    
    @Transactional(rollbackFor = Exception.class)
    public int deleteExpiredRecords(Date deleteRecordDate) {
        return followTransactionRecordDOExtendMapper.deleteExpiredRecords(deleteRecordDate);
    }
}
