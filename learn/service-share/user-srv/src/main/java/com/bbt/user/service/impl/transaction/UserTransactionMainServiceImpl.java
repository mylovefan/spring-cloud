package com.bbt.user.service.impl.transaction;

import com.bbt.mq.annotation.CompensatingTransactionMain;
import com.bbt.mq.constants.MqConstant;
import com.bbt.mq.dto.user.UserCreateOrderNotification;
import com.bbt.mq.dto.user.reply.UserCreateOrderReply;
import com.bbt.mq.service.impl.AbstractTransactionMainService;
import com.bbt.user.constants.TransactionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 发送消息主事物
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 14:29
 */
@Service("userTransactionMainServiceImpl")
@CompensatingTransactionMain(value = TransactionConstant.USER_CREATE_ORDER, topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_USER_CREATE_ORDER)
public class UserTransactionMainServiceImpl extends AbstractTransactionMainService<UserCreateOrderNotification,UserCreateOrderReply> {

    private static Logger logger = LoggerFactory.getLogger(UserTransactionMainServiceImpl.class);

    @Override
    public UserCreateOrderNotification selectNotification(Long relateId) {

        UserCreateOrderNotification notification = new UserCreateOrderNotification();
        notification.setId(relateId);
        notification.setCount(100);
        notification.setUserNum("A123456");
        return notification;
    }


    @Override
    public void afterSuccess(Long relateId, UserCreateOrderReply reply) {

        logger.info("消息回调成功"+reply.isFlag());
    }
}
