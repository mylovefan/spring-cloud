package com.bbt.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.bbt.framework.web.BaseController;
import com.bbt.framework.web.Result;
import com.bbt.user.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TransportClient client;


    @RequestMapping(value = "register", method = RequestMethod.GET)
    public Result register(@RequestParam String account,@RequestParam String pwd) {
        userService.register(account,pwd);
        return successCreated();
    }


    @RequestMapping(value = "getUserById", method = RequestMethod.GET)
    public Result<String> getUserById(@RequestParam Integer id) {
        GetResponse result= this.client.prepareGet("user","users",id.toString()).get();
        ResponseEntity entity = new ResponseEntity(result.getSource(), HttpStatus.OK);
        String jsonStr= JSONObject.toJSONString(entity.getBody());
        JSONObject json = JSONObject.parseObject(jsonStr);
        return successGet(json.getString("account"));
    }

}
