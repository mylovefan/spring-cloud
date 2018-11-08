package com.bbt.user.api.controller;

import com.bbt.authorization.annotation.Authorization;
import com.bbt.authorization.manager.TokenManager;
import com.bbt.authorization.util.UserUtil;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.constans.UserConstant;
import com.bbt.user.api.result.TokenDTO;
import com.bbt.user.api.result.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 10:37
 */
@Api(tags = "user", value = "永久接口")
@RestController
@RequestMapping(value = "user/")
public class UserController extends BaseController{

    @Autowired
    private TokenManager tokenManager;

    @SuppressWarnings("unchecked")
    @Authorization
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息,[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = 200, message = "success")
    @RequestMapping(value = "getUserInfo", method = RequestMethod.GET)
    public Result<UserDTO> getUserInfo(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @RequestParam @ApiParam(required = true, value = "test") String str) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(UserUtil.getCurrentUserId(getRequest()));
        userDTO.setUserNum(UserUtil.getCurrentUserNum(getRequest()));
        userDTO.setAccount(UserUtil.getCurrentAccount(getRequest()));
        return successGet(userDTO);
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "登录", notes = "login,[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = 200, message = "success")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Result<TokenDTO> login(@RequestParam @ApiParam(required = true, value = "account") String account,
                                  @RequestParam @ApiParam(required = true, value = "pwd") String pwd) {

        String token = tokenManager.createToken("TEACHER","T123456",1L,account);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setUserNum("T123456");
        return successGet(tokenDTO);
    }


}
