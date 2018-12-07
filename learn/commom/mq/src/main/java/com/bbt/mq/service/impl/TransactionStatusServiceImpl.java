package com.bbt.mq.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bbt.mq.TransactionProperties;
import com.bbt.mq.bo.TransactionRecordBO;
import com.bbt.mq.domain.TransactionRecordDO;
import com.bbt.mq.domain.TransactionRecordDOExample;
import com.bbt.mq.mapper.TransactionRecordDOMapper;
import com.bbt.mq.mapper.extend.TransactionRecordDOExtendMapper;
import com.bbt.mq.service.TransactionStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:21
 */
@Service
public class TransactionStatusServiceImpl implements TransactionStatusService {
    
    private static final Logger log = LoggerFactory.getLogger(TransactionStatusServiceImpl.class);
    
	@Autowired
	private TransactionRecordDOMapper transactionRecordDOMapper;
	
    @Autowired
    private TransactionRecordDOExtendMapper transactionRecordDOExtendMapper;
	
    @Autowired
    private TransactionProperties transactionProperties;
    
    @Autowired
    private TransactionStatusServiceImpl transactionStatusServiceImpl;

	@Transactional(rollbackFor = Exception.class)	
	@Override
	public Long save(Long relateId, byte type) {
	    // 数据的唯一性由唯一索引保证
		TransactionRecordDO transactionRecord = new TransactionRecordDO();
		transactionRecord.setIsProcessed(false);
		transactionRecord.setRelateId(relateId);
		transactionRecord.setType(type);
		transactionRecord.setGmtModified(new Date());
		transactionRecord.setGmtCreate(new Date());
		transactionRecord.setTimes(0L);
		transactionRecordDOMapper.insertSelective(transactionRecord);
		return transactionRecord.getId();
	}

	@Transactional(rollbackFor = Exception.class)	
	@Override
	public Long success(Long transactionId) {
		TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
		if (transactionRecord == null) {
			throw new RuntimeException("transactionId is null");
		}
		// 是否处理完成
		if (transactionRecord.getIsProcessed()) {
			return null;
		}
		
		TransactionRecordDO transactionRecordUpdateDO = new TransactionRecordDO();
		transactionRecordUpdateDO.setId(transactionRecord.getId());
		transactionRecordUpdateDO.setIsProcessed(true);
		transactionRecordUpdateDO.setGmtModified(new Date());
		transactionRecordDOMapper.updateByPrimaryKeySelective(transactionRecordUpdateDO);
		return transactionRecord.getRelateId();
	}

	@Override
	public List<TransactionRecordBO> selectNotDoneList(byte type, Long exectotalCount) {
		TransactionRecordDOExample example = new TransactionRecordDOExample();
		example.createCriteria().andTypeEqualTo(type).andIsProcessedEqualTo(false).andTimesLessThan(exectotalCount);
		List<TransactionRecordDO> transactionRecordDOS = transactionRecordDOMapper.selectByExample(example);
		List<TransactionRecordBO> notDoneList = new ArrayList<>();
		for (int i = 0; i < transactionRecordDOS.size(); i++) {
			TransactionRecordDO transactionRecordDO = transactionRecordDOS.get(i);
			TransactionRecordBO transactionRecordBO = new TransactionRecordBO();
			transactionRecordBO.setId(transactionRecordDO.getId());
			transactionRecordBO.setRelateId(transactionRecordDO.getRelateId());
			transactionRecordBO.setTimes(transactionRecordDO.getTimes());
			notDoneList.add(transactionRecordBO);
		}
		return notDoneList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateTimes(Long transactionId, Long times) {
		TransactionRecordDO record = new TransactionRecordDO();
		record.setId(transactionId);
		record.setTimes(times);
		record.setGmtModified(new Date());
		transactionRecordDOMapper.updateByPrimaryKeySelective(record);
	}
	
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
                int affectedRows = transactionStatusServiceImpl.deleteExpiredRecords(calendar.getTime());
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
        return transactionRecordDOExtendMapper.deleteExpiredRecords(deleteRecordDate);
    }

    @Override
    public Boolean isSuccess(Long transactionId) {
        TransactionRecordDO transactionRecord = transactionRecordDOMapper.selectByPrimaryKey(transactionId);
        if (transactionRecord == null) {
            throw new RuntimeException("transactionRecord is null by transactionId:" + transactionId);
        }
        return transactionRecord.getIsProcessed();
    }
}
