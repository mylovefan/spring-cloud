package com.bbt.user.api.controller;

import com.bbt.authorization.annotation.Authorization;
import com.bbt.authorization.manager.TokenManager;
import com.bbt.authorization.util.UserUtil;
import com.bbt.concurrentqueue.requestctrl.AbstractBaseConcurrentTask;
import com.bbt.concurrentqueue.requestctrl.ConcurrentTaskExecutor;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.HttpCode;
import com.bbt.framework.web.Result;
import com.bbt.framework.web.constans.UserConstant;
import com.bbt.user.api.result.TokenDTO;
import com.bbt.user.api.result.UserDTO;
import com.bbt.user.api.service.UserExtendService;
import com.bbt.user.api.service.UserService;
import com.bbt.user.dto.OrderDTO;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserExtendService userExtendService;

    @Autowired
    private ConcurrentTaskExecutor concurrentTaskExecutor;

    @SuppressWarnings("unchecked")
    @Authorization
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息,[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
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
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public Result<TokenDTO> login(@RequestParam @ApiParam(required = true, value = "account") String account,
                                  @RequestParam @ApiParam(required = true, value = "pwd") String pwd) {

        String token = tokenManager.createToken("TEACHER","T123456",1L,account);
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setUserNum("T123456");
        return successGet(tokenDTO);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "退出", notes = "清除token,[]（张荣成）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "logout", method = RequestMethod.DELETE)
    public Result logout(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        tokenManager.delRelationshipByToken(token);
        return successDelete();
    }


    @ApiOperation(value = "注册", notes = "注册,[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestParam @ApiParam(required = true, value = "account") String account, @RequestParam @ApiParam(required = true, value = "pwd") String pwd) {
        userService.register(account,pwd);
        return successCreated();
    }

    @ApiOperation(value = "mq测试", notes = "mq测试,[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "user/createOrderById", method = RequestMethod.GET)
    Result createOrderById(@RequestParam  @ApiParam(required = true, value = "id") Long id){
        userService.createOrder(id);
        return successCreated();
    }


    @ApiOperation(value = "es查询", notes = "es查询,[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    public Result<String> getUserById( @RequestParam @ApiParam(required = true, value = "id") Integer id) {
        Result<String> result = userService.getUserById(id);
        return successGet(result);
    }


    @ApiOperation(value = "添加数据到ES", notes = "添加数据到ES,[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "saveUserEs", method = RequestMethod.POST)
    public Result<String> saveUserEs(@RequestParam @ApiParam(required = true, value = "account") String account, @RequestParam @ApiParam(required = true, value = "pwd") String pwd) {
        Result<String> result = userService.saveUserEs(account,pwd);
        return successCreated(result);
    }


    @ApiOperation(value = "并发组件测试", notes = "并发组件测试,[]（张荣成）", httpMethod = "PUT")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "createOrder/{id}", method = RequestMethod.PUT)
    public Result<OrderDTO> createOrder(@PathVariable @ApiParam(required = true, value = "id") Long id) {
        Result<OrderDTO> res = null;
        res = (Result<OrderDTO>)concurrentTaskExecutor.execute(new AbstractBaseConcurrentTask<Result<OrderDTO>,Result<OrderDTO>>(){
            @Override
            public Result<OrderDTO> execute() {
                return userExtendService.createOrder(id);
            }

            @Override
            public Result<OrderDTO> executeWhenSuccess(Result<OrderDTO> successInfo) {
                return successInfo;
            }
        });
        return res;
    }





}
