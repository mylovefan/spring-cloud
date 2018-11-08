package com.bbt.authorization.util;

import com.bbt.authorization.interceptor.AuthorizationInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 15:04
 */
public class UserUtil {

    /**
     * 获取当前登录用户账号
     *
     * @param request
     * @return
     */
    public static String getCurrentAccount(HttpServletRequest request) {
        Object account = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_ACCOUNT);
        return account == null ? null : account.toString();
    }

    /**
     * 获取当前登录用户编号
     *
     * @param request
     * @return
     */
    public static String getCurrentUserNum(HttpServletRequest request) {
        Object userNum = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_NUM);
        return userNum == null ? null : userNum.toString();
    }

    /**
     * 获取当前登录用户ID
     *
     * @param request
     * @return
     */
    public static Long getCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute(AuthorizationInterceptor.REQUEST_CURRENT_USER_ID);
        if (userId == null) {
            return 0L;
        }

        return Long.valueOf(userId.toString());
    }
}
