package com.bbt.authorization.manager;

import com.bbt.authorization.constants.TokenClearType;

/**
 * @Description: 对Token进行管理的接口
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 15:13
 */
public interface TokenManager {

    /**
     * 创建token
     * @param type
     * @param userNo
     * @param userId
     * @param account
     */
    String createToken(String type, String userNo, Long userId, String account);


    /**
     * 通过token获得对应的key
     * @param token
     * @return
     */
    String getAccount(String token);


    /**
     * 如token存在，但鉴权失败时通过该接口获取失败原因
     *
     * @param token
     * @return
     */
    TokenClearType getTokenClearType(String token);


    /**
     * 通过token删除关联关系
     * @param token
     */
    void delRelationshipByToken(String token);

    /**
     * 通过key删除关联关系
     * @param account
     * @param tokenClearType
     */
    void delRelationship(String account, TokenClearType tokenClearType);


}
