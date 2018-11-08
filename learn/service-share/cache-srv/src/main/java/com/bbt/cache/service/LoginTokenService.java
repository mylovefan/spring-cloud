package com.bbt.cache.service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 10:14
 */
public interface LoginTokenService {

    /**
     *
     * 通用参数说明：
     * userLoginType 用户类型：由外部保证不同调用者不同值（会员、商家、代理商。。。）
     * account 登录账号
     * token 生成的token
     * expireSeconds token超时时间
     * tokenClearType token清除类型/原因，为空表示不做记录
     */

    /**
     * 单客户端登录情况下的token映射
     * 需要同时记录两个映射 account-token，token-account
     *
     * @param userLoginType
     * @param account
     * @param token
     * @param expireSeconds
     * @param tokenClearType 该方法会删除掉之前登录过产生的token，此处为旧token删除原因
     */
    void setTokenOneToOne(Integer userLoginType, String account, String token, Integer expireSeconds, Integer tokenClearType);

    /**
     * 多客户端登录情况下的token映射
     * 只需记录token-account
     *
     * @param userLoginType
     * @param account
     * @param token
     * @param expireSeconds
     */
    void setTokenOneToMany(Integer userLoginType, String account, String token, Integer expireSeconds);

    /**
     * 通过token获取账号
     *
     * @param userLoginType
     * @param token
     * @param flushExpireAfterOperation 是否刷新超时时间
     * @param expireSeconds
     * @param singleTokenWithUser 是否单客户端登录
     * @return
     */
    String getAccount(Integer userLoginType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    /**
     * 获取token被删除原因
     *
     * @param userLoginType
     * @param token
     * @return
     */
    Integer getTokenClearType(Integer userLoginType, String token);

    /**
     * 通过账号删除token（仅适用于单客户端登录）
     *
     * @param userLoginType
     * @param account
     * @param expireSeconds
     * @param tokenClearType 删除原因
     */
    void delRelationshipByAccount(Integer userLoginType, String account, Integer expireSeconds, Integer tokenClearType);

    /**
     * 通过token删除账号信息
     *
     * @param userLoginType
     * @param token
     * @param singleTokenWithUser 是否单客户端登录
     * @param expireSeconds
     * @param tokenClearType 删除原因
     */
    void delRelationshipByToken(Integer userLoginType, String token, Boolean singleTokenWithUser, Integer expireSeconds, Integer tokenClearType);

}
