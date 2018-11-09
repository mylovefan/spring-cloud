package com.bbt.user.controller;

import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import com.bbt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/9 17:06
 */
@RestController
@RequestMapping(value = "user/")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public Result register(@RequestParam String account,@RequestParam String pwd) {
        userService.register(account,pwd);
        return successCreated();
    }

}
