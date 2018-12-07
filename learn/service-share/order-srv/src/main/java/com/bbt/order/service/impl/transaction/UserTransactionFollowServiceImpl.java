package com.bbt.order.service.impl.transaction;

import com.bbt.mq.annotation.CompensatingTransactionFollow;
import com.bbt.mq.constants.MqConstant;
import com.bbt.mq.dto.user.UserCreateOrderNotification;
import com.bbt.mq.dto.user.reply.UserCreateOrderReply;
import com.bbt.mq.service.impl.AbstractTransactionFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Description: 从事物
 * @Author:  zhangrc
 * @CreateDate:  2018/11/30 14:39
 */
@Service
@CompensatingTransactionFollow(topic = MqConstant.TOPIC_USER_SRV, tags = MqConstant.TAG_USER_CREATE_ORDER)
public class UserTransactionFollowServiceImpl extends AbstractTransactionFollowService<UserCreateOrderNotification,UserCreateOrderReply> {

    Logger logger = LoggerFactory.getLogger(UserTransactionFollowServiceImpl.class);

    @Override
    public void execute(UserCreateOrderNotification notification) {
        logger.info("消息已消费--------"+notification.getId());
    }

    @Override
    public UserCreateOrderReply getReply(UserCreateOrderNotification notification) {
        UserCreateOrderReply orderReply = new UserCreateOrderReply();
        orderReply.setFlag(true);
        return orderReply;
    }
}
