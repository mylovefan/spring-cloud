package com.bbt.mq;

import java.io.Serializable;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:05
 */
public class Reply implements Serializable {

    private static final long serialVersionUID = -2679454453968450127L;

    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
