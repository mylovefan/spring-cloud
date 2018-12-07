package com.bbt.authorization.manager.impl;

import com.bbt.authorization.constants.TokenClearType;
import com.bbt.authorization.manager.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description: 使用缓存存储TOKEN
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 17:21
 */
@Component
public class CacheTokenManager extends AbstractTokenManager{

    @Autowired
    private TokenCacheService tokenCacheService;

    @Override
    protected void createMultipleRelationship(String account, String token) {

        tokenCacheService.setTokenOneToMany(account, token, tokenExpireSeconds);
    }

    @Override
    protected void createSingleRelationship(String account, String token) {

        tokenCacheService.setTokenOneToOne(account, token, tokenExpireSeconds);
    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        return tokenCacheService.getTokenClearType(token);
    }

    @Override
    protected String getAccountByToken(String token, boolean flushExpireAfterOperation) {
        return tokenCacheService.getAccount(token, flushExpireAfterOperation, tokenExpireSeconds, singleTokenWithUser);
    }

    @Override
    public void delRelationshipByToken(String token) {
        tokenCacheService.delRelationshipByToken(token, singleTokenWithUser, tokenExpireSeconds);
    }


    @Override
    protected void delSingleRelationshipByKey(String account, TokenClearType tokenClearType) {
        tokenCacheService.delRelationshipByAccount(account, tokenExpireSeconds, tokenClearType);
    }
}
