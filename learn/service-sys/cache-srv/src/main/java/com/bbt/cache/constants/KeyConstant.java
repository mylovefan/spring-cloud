package com.bbt.cache.constants;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 10:17
 */
public class KeyConstant {

    /**
     * 账号-token
     */
    public static final String REDIS_ACCOUNT_PREFIX = "AUTH_ACCOUNT_";

    /**
     * token-账号
     */
    public static final String REDIS_TOKEN_PREFIX = "AUTH_TOKEN_";

    /**
     * token-删除原因
     */
    public static final String REDIS_TOKEN_CLEAR_PREFIX = "AUTH_TOKEN_DEL_";
}
