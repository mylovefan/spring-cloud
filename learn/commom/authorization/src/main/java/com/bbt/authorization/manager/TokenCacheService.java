package com.bbt.authorization.manager;

import com.bbt.authorization.constants.TokenClearType;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 17:22
 */
public interface TokenCacheService {

    void setTokenOneToOne(String account, String token, Integer expireSeconds);

    void setTokenOneToMany(String account, String token, Integer expireSeconds);

    String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser);

    void delRelationshipByAccount(String account, Integer expireSeconds, TokenClearType tokenClearType);

    void delRelationshipByToken(String token, Boolean singleTokenWithUser, Integer expireSeconds);

    TokenClearType getTokenClearType(String token);
}
