package com.bbt.user.api.service;

import com.bbt.framework.web.Result;
import com.bbt.user.dto.UserDTO;
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


    @RequestMapping(value = "user/getUserById", method = RequestMethod.GET)
    Result<String> getUserById(@RequestParam("id") Integer id);


    @RequestMapping(value = "user/saveUserEs", method = RequestMethod.POST)
    Result<String> saveUserEs(@RequestParam("account") String account,@RequestParam("pwd") String pwd);

    @RequestMapping(value = "user/query", method = RequestMethod.GET)
    Result<UserDTO> query(@RequestParam("author") String author, @RequestParam("title") String title,
                          @RequestParam("gtWoedCount") int gtWoedCount , @RequestParam("ltWordCount") Integer ltWordCount);


    @RequestMapping(value = "user/createOrder", method = RequestMethod.GET)
    Result createOrder(@RequestParam("id") Long id);

}
