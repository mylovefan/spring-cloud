package com.bbt.framework.web;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 10:08
 */
public class ResultCode extends BaseResultCode{


    public static final int USER_UNAUTHORIZED = 3000;

    public static final int USER_ACCOUNT_DISABLE = 3001;

    public static final int USER_WRONG_PWD = 3002;

    // 初始化状态码与文字说明
    static {
        messageMap.put(USER_UNAUTHORIZED, "未授权");

        messageMap.put(USER_ACCOUNT_DISABLE, "账号已被禁用");

        messageMap.put(USER_WRONG_PWD, "密码错误");

    }
}
