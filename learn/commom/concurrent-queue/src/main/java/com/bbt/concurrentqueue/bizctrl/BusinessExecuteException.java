package com.bbt.concurrentqueue.bizctrl;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/27 17:54
 */
public class BusinessExecuteException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    private int ret;
    private String msg;

    public BusinessExecuteException(int ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public BusinessExecuteException(Throwable cause, int ret, String msg) {
        super(cause);
        this.ret = ret;
        this.msg = msg;
    }

    public BusinessExecuteException(Throwable cause) {
        super(cause);
    }

    public BusinessExecuteException() {
        super();
    }


    public int getRet() {
        return ret;
    }

    public String getMsg() {
        return msg;
    }

}
