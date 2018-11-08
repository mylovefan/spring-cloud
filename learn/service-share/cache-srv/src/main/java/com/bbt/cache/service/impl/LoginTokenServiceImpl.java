package com.bbt.cache.service.impl;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.bbt.cache.constants.KeyConstant;
import com.bbt.cache.service.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


/**
 * @author Leach
 * @date 2017/10/11
 */
@Service
public class LoginTokenServiceImpl implements LoginTokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /*@Autowired
    private RedisTemplate<String, Integer> redisTemplate;*/

    @Override
    public void setTokenOneToOne(Integer userLoginType, String account, String token, Integer expireSeconds, Integer tokenClearType) {

        String accountKey = formatAccountKey(userLoginType, account);

        String oldToken = getValue(accountKey);
        if (oldToken != null) {
            String oldTokenKey = formatTokenKey(userLoginType, oldToken);
            stringRedisTemplate.delete(oldTokenKey);
            recordTokenClearType(userLoginType, oldToken, expireSeconds, tokenClearType);
        }
        stringRedisTemplate.opsForValue().set(accountKey, token, expireSeconds, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(formatTokenKey(userLoginType, token), account, expireSeconds, TimeUnit.SECONDS);

    }

    @Override
    public void setTokenOneToMany(Integer userLoginType, String account, String token, Integer expireSeconds) {
        stringRedisTemplate.opsForValue().set(formatTokenKey(userLoginType, token), account, expireSeconds, TimeUnit.SECONDS);
    }

    @Override
    public String getAccount(Integer userLoginType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        String tokenKey = formatTokenKey(userLoginType, token);
        String account = getValue(tokenKey);
        //根据设置，在每次有效操作后刷新过期时间
        if (account != null && flushExpireAfterOperation) {
            if (singleTokenWithUser) {
                stringRedisTemplate.expire(formatAccountKey(userLoginType, account), expireSeconds, TimeUnit.SECONDS);

            }
            stringRedisTemplate.expire(tokenKey, expireSeconds, TimeUnit.SECONDS);

        }
        return account;
    }

    @Override
    public Integer getTokenClearType(Integer userLoginType, String token) {
        String value = stringRedisTemplate.opsForValue().get(formatTokenClearKey(userLoginType, token));
        return value == null ? null : Integer.valueOf(value);
    }

    @Override
    public void delRelationshipByAccount(Integer userLoginType, String account, Integer expireSeconds, Integer tokenClearType) {
        String accountKey = formatAccountKey(userLoginType, account);
        String token = getValue(accountKey);
        if (token != null) {
            stringRedisTemplate.delete(Arrays.asList(accountKey, formatTokenKey(userLoginType, token)));
            recordTokenClearType(userLoginType, token, expireSeconds, tokenClearType);
        }
    }

    @Override
    public void delRelationshipByToken(Integer userLoginType, String token, Boolean singleTokenWithUser, Integer expireSeconds, Integer tokenClearType) {
        String tokenKey = formatTokenKey(userLoginType, token);

        if (singleTokenWithUser) {
            String account = getValue(tokenKey);
            stringRedisTemplate.delete(Arrays.asList(formatAccountKey(userLoginType, account), tokenKey));
        } else {
            stringRedisTemplate.delete(tokenKey);
        }
        recordTokenClearType(userLoginType, token, expireSeconds, tokenClearType);
    }

    /**
     * 记录token清除类型（即退出原因）
     *
     * @param userLoginType
     * @param token
     * @param expireSeconds
     * @param tokenClearType
     */
    private void recordTokenClearType(Integer userLoginType, String token, Integer expireSeconds, Integer tokenClearType) {
        if (tokenClearType != null) {
            stringRedisTemplate.opsForValue().set(formatTokenClearKey(userLoginType, token), String.valueOf(tokenClearType), expireSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     *
     * 通过key获取缓存中的字符串值
     * @param key
     * @return
     */
    private String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 拼接账号key
     *
     * @param type
     * @param account
     * @return
     */
    private String formatAccountKey(int type, String account) {
        return KeyConstant.REDIS_ACCOUNT_PREFIX.concat(String.valueOf(type) + "_").concat(account);
    }

    /**
     * 拼接token keyLawu_1301
     *
     * @param type
     * @param token
     * @return
     */
    private String formatTokenKey(int type, String token) {
        return KeyConstant.REDIS_TOKEN_PREFIX.concat(String.valueOf(type) + "_").concat(token);
    }

    /**
     * 拼接token删除类型 key
     *
     * @param type
     * @param token
     * @return
     */
    private String formatTokenClearKey(int type, String token) {
        return KeyConstant.REDIS_TOKEN_CLEAR_PREFIX.concat(String.valueOf(type) + "_").concat(token);
    }

}
