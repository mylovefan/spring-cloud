package com.bbt.mq.mapper.extend;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * 主事务表扩展Mapper
 *
 */
public interface TransactionRecordDOExtendMapper {

    /**
     * 根据时间点物理删除这个时间点一个月之前的事务记录
     * @param deleteRecordDate 时间点
     */
    int deleteExpiredRecords(@Param("deleteRecordDate") Date deleteRecordDate);
}