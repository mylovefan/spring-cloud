package com.bbt.mq;

import java.io.Serializable;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 15:10
 */
public class Notification implements Serializable {

    private static final long serialVersionUID = 6363486881966391317L;

    private Long transactionId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }
}
