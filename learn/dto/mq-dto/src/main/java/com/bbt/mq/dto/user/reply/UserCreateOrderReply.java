package com.bbt.mq.dto.user.reply;

import com.bbt.mq.Reply;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:41
 */
public class UserCreateOrderReply extends Reply{

    private static final long serialVersionUID = -5142282166586603538L;

    /**
     * 是否成功
     */
    private boolean flag;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
