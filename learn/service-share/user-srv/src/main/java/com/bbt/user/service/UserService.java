package com.bbt.user.service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/9 16:59
 */
public interface UserService {

    /**
     * 注册
     *
     * @param account
     * @param pwd
     */
    void register(String account,String pwd);
}
