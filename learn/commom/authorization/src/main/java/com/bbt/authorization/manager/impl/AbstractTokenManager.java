package com.bbt.authorization.manager.impl;

import com.bbt.authorization.manager.TokenManager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 15:31
 */
@Component
public abstract class AbstractTokenManager implements TokenManager {


    protected int tokenExpireSeconds = 7 * 24 * 3600;

    protected boolean singleTokenWithUser = true;

    protected boolean flushExpireAfterOperation = true;

    public static final String TOKEN_KEY = "sdfhsfhgyvcw%#$^%783624wGFGEJH";

    @Override
    public String createToken(String type, String userNo, Long userId, String account) {

        String id = String.valueOf(userId);

        String token = Jwts
                .builder()
                .setIssuer(type)
                .setId(userNo)
                .setSubject(account)
                .setAudience(id)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(SignatureAlgorithm.HS512, AbstractTokenManager.TOKEN_KEY)
                .compact();

        // 根据设置的每个用户是否只允许绑定一个Token，调用不同的方法
        if (singleTokenWithUser) {
            //delSingleRelationshipByKey(account);
            createSingleRelationship(account, token);
        } else {
            createMultipleRelationship(account, token);
        }
        return token;
    }

    /**
     * 一个用户可以绑定多个Token时创建关联关系
     */
    protected abstract void createMultipleRelationship(String account, String token);

    /**
     * 一个用户只能绑定一个Token时创建关联关系
     */
    protected abstract void createSingleRelationship(String account, String token);

    @Override
    public String getAccount(String token) {

        String account = getAccountByToken(token, flushExpireAfterOperation);
        return account;
    }

    /**
     * 通过Token获得Key
     */
    protected abstract String getAccountByToken(String token, boolean flushExpireAfterOperation);



    public int getTokenExpireSeconds() {
        return tokenExpireSeconds;
    }

    public void setTokenExpireSeconds(int tokenExpireSeconds) {
        this.tokenExpireSeconds = tokenExpireSeconds;
    }

    public boolean isSingleTokenWithUser() {
        return singleTokenWithUser;
    }

    public void setSingleTokenWithUser(boolean singleTokenWithUser) {
        this.singleTokenWithUser = singleTokenWithUser;
    }

    public boolean isFlushExpireAfterOperation() {
        return flushExpireAfterOperation;
    }

    public void setFlushExpireAfterOperation(boolean flushExpireAfterOperation) {
        this.flushExpireAfterOperation = flushExpireAfterOperation;
    }
}
