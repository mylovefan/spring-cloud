package com.bbt.authorization.manager.impl;

import com.bbt.authorization.constants.TokenClearType;
import com.bbt.authorization.manager.LoginTokenService;
import com.bbt.authorization.manager.TokenCacheService;
import com.bbt.framework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 17:54
 */
@Component
public class RedisTokenCacheServiceImpl implements TokenCacheService {

    @Autowired
    private LoginTokenService loginTokenService;

    /**
     * 缓存超时时间
     */
    private Integer expireSeconds = 86400;

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }


    @Override
    public void setTokenOneToOne(String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToOne(1,account,token,expireSeconds == null ? this.expireSeconds : expireSeconds ,TokenClearType.LOGOUT_PASSIVE.getVal());
    }

    @Override
    public void setTokenOneToMany(String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToMany(1, account, token, expireSeconds == null ? this.expireSeconds : expireSeconds);

    }

    @Override
    public String getAccount(String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        Result<String> result = loginTokenService.getAccount(1, token, flushExpireAfterOperation, expireSeconds == null ? this.expireSeconds : expireSeconds, singleTokenWithUser);
        return result.getModel();
    }

    @Override
    public void delRelationshipByAccount(String account, Integer expireSeconds, TokenClearType tokenClearType) {
        loginTokenService.delRelationshipByAccount(1, account, expireSeconds == null ? this.expireSeconds : expireSeconds, tokenClearType == null ? null : tokenClearType.getVal());

    }

    @Override
    public void delRelationshipByToken(String token, Boolean singleTokenWithUser, Integer expireSeconds) {
        loginTokenService.delRelationshipByToken(1, token, singleTokenWithUser, expireSeconds == null ? this.expireSeconds : expireSeconds, null);

    }

    @Override
    public TokenClearType getTokenClearType(String token) {
        Result<Integer> result = loginTokenService.getTokenClearType(1, token);
        Integer tokenClearType = result.getModel();
        return tokenClearType == null ? null : TokenClearType.getEnum(tokenClearType);
    }
}
