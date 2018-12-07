package com.bbt.mq;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 事务补偿属性类
 *
 */
@ConfigurationProperties
@Component
public class TransactionProperties {
    
    /**
     * MQ消息有效时间(单位:毫秒)
     * 60秒
     */
    private long messageValidTime = 60 * 1000L;
    
    /**
     * 事务记录删除时间(单位:天)
     * 30天两个月
     */
    private Integer deleteRecordTime = 30;
    
    /**
     * 执行Sql语句
     */
    private Boolean executeSqlStatement = false;
    
    private TransactionJob job = new TransactionJob();

    public long getMessageValidTime() {
        return messageValidTime;
    }

    public void setMessageValidTime(long messageValidTime) {
        this.messageValidTime = messageValidTime;
    }
    
    public Integer getDeleteRecordTime() {
        return deleteRecordTime;
    }

    public void setDeleteRecordTime(Integer deleteRecordTime) {
        this.deleteRecordTime = deleteRecordTime;
    }
    
    public Boolean getExecuteSqlStatement() {
        return executeSqlStatement;
    }

    public void setExecuteSqlStatement(Boolean executeSqlStatement) {
        this.executeSqlStatement = executeSqlStatement;
    }

    public TransactionJob getJob() {
        return job;
    }

    public void setJob(TransactionJob job) {
        this.job = job;
    }

    public static class TransactionJob {
        
        /**
         * 间隔基数（次数）
         */
        private Long intervalBaseNumber = 2L;
        
        /**
         * 执行最大次数
         */
        private Long exectotalCount = 10L;
        
        /**
         * 是否禁用
         */
        private Boolean disabled = false;
        
        public Long getIntervalBaseNumber() {
            return intervalBaseNumber;
        }

        public void setIntervalBaseNumber(Long intervalBaseNumber) {
            this.intervalBaseNumber = intervalBaseNumber;
        }

        public Long getExectotalCount() {
            return exectotalCount;
        }

        public void setExectotalCount(Long exectotalCount) {
            this.exectotalCount = exectotalCount;
        }

        public Boolean getDisabled() {
            return disabled;
        }

        public void setDisabled(Boolean disabled) {
            this.disabled = disabled;
        }

    }
}