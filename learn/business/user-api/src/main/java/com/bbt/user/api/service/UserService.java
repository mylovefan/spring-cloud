package com.bbt.user.api.service;

import com.bbt.framework.web.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/9 17:11
 */
@FeignClient(value = "user-srv")
public interface UserService {

    @RequestMapping(value = "user/register", method = RequestMethod.GET)
    Result register(@RequestParam("account") String account, @RequestParam("pwd") String pwd);
}
