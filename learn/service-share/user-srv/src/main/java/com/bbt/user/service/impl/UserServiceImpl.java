package com.bbt.user.service.impl;

import com.bbt.user.domain.UserDO;
import com.bbt.user.mapper.UserDOMapper;
import com.bbt.user.service.UserService;
import com.bbt.user.util.PwdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/9 17:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String account, String pwd) {

        UserDO user = new UserDO();
        user.setAccount(account);
        user.setPwd(PwdUtil.generate(pwd));
        userDOMapper.insertSelective(user);
    }
}
