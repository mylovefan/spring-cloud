package com.bbt.mq.bo;

public class TransactionRecordBO {
	
    /**
     * 主键
     */
    private Long id;

    /**
     * 关联ID
     */
    private Long relateId;
    
    /**
    * 执行次数
    */
    private Long times;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRelateId() {
		return relateId;
	}

	public void setRelateId(Long relateId) {
		this.relateId = relateId;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}
}