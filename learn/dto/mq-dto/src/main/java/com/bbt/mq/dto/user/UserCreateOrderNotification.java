package com.bbt.mq.dto.user;

import com.bbt.mq.Notification;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 16:36
 */
public class UserCreateOrderNotification extends Notification {

    private static final long serialVersionUID = -1487164000601816456L;

    private Long id;

    private String userNum;

    private Integer count;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
