package com.bbt.cache.controller;

import com.bbt.cache.service.LoginTokenService;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/8 10:27
 */
@RestController
@RequestMapping(value = "loginToken/")
public class LoginTokenController extends BaseController {

    @Autowired
    private LoginTokenService loginTokenService;

    @RequestMapping(value = "setTokenOneToOne", method = RequestMethod.PUT)
    public Result setTokenOneToOne(Integer userLoginType, String account, String token, Integer expireSeconds, @RequestParam(required = false) Integer tokenClearType) {
        loginTokenService.setTokenOneToOne(userLoginType, account, token, expireSeconds, tokenClearType);
        return successCreated();
    }

    @RequestMapping(value = "setTokenOneToMany", method = RequestMethod.PUT)
    public Result setTokenOneToMany(Integer userLoginType, String account, String token, Integer expireSeconds) {
        loginTokenService.setTokenOneToMany(userLoginType, account, token, expireSeconds);
        return successCreated();
    }

    @RequestMapping(value = "getAccount", method = RequestMethod.GET)
    public Result<String> getAccount(Integer userLoginType, String token, Boolean flushExpireAfterOperation, Integer expireSeconds, Boolean singleTokenWithUser) {
        String account = loginTokenService.getAccount(userLoginType, token, flushExpireAfterOperation, expireSeconds, singleTokenWithUser);
        return successGet(account);
    }

    @RequestMapping(value = "getTokenClearType", method = RequestMethod.GET)
    public Result<Integer> getTokenDelReason(Integer userLoginType, String token) {
        Integer tokenClearType = loginTokenService.getTokenClearType(userLoginType, token);
        tokenClearType = tokenClearType == null ? 0 : tokenClearType;
        return successGet(tokenClearType);
    }

    @RequestMapping(value = "delRelationshipByAccount", method = RequestMethod.DELETE)
    public Result delRelationshipByAccount(Integer userLoginType, String account, Integer expireSeconds, @RequestParam(required = false) Integer tokenClearType) {
        loginTokenService.delRelationshipByAccount(userLoginType, account, expireSeconds, tokenClearType);
        return successDelete();
    }

    @RequestMapping(value = "delRelationshipByToken", method = RequestMethod.DELETE)
    public Result delRelationshipByToken(Integer userLoginType, String token, Boolean singleTokenWithUser, Integer expireSeconds, @RequestParam(required = false) Integer tokenClearType) {
        loginTokenService.delRelationshipByToken(userLoginType, token, singleTokenWithUser, expireSeconds, tokenClearType);
        return successDelete();
    }

}
