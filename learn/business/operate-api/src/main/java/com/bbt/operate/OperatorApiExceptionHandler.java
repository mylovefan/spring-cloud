package com.bbt.operate;

import com.bbt.framework.web.Result;
import com.bbt.framework.web.ResultCode;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bbt.framework.web.BaseController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 10:04
 */
@ControllerAdvice
public class OperatorApiExceptionHandler extends BaseController{

    private Logger logger = LoggerFactory.getLogger(OperatorApiExceptionHandler.class);

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public Result defaultUnauthenticatedHandler(UnauthenticatedException e) throws Exception {
        return failUnauthorized(ResultCode.USER_UNAUTHORIZED);
    }
}
