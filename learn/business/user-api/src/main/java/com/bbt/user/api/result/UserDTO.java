package com.bbt.user.api.result;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 15:06
 */
public class UserDTO {
    private Long id;

    private String userNum;

    private String account;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
