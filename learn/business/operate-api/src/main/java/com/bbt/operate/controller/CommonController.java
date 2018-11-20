package com.bbt.operate.controller;

import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.HttpCode;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 11:44
 */
@Api(tags = "common")
@RestController
@RequestMapping(value = "/")
public class CommonController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);


    @ApiOperation(value = "登录", notes = "根据账号密码登录。[2000]（张荣成）", httpMethod = "GET")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Result login(String account, String password) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(account, password));
            return successCreated();
        }catch (LockedAccountException lae) {
            logger.error("账号已停用：" + lae);
            return successCreated(ResultCode.USER_ACCOUNT_DISABLE);
        }catch (AuthenticationException e) {
            logger.error("用户名或密码错误：" + e);
            return successCreated(ResultCode.USER_WRONG_PWD);
        }
    }


    @ApiOperation(value = "测试权限", notes = "测试权限。[2000]（张荣成）", httpMethod = "GET")
    @RequiresPermissions("user:add")
    //@RequiresPermissions("user:edit")  则会提示无此权限
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "testAuth", method = RequestMethod.GET)
    public Result testAuth() {
        return successCreated();
    }


}
