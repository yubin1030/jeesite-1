package com.thinkgem.jeesite.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by yubin on 17/3/30.
 */
public class TestQueueConsumerTopic implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(TestQueueConsumerTopic.class);
    @Override
    public void onMessage(Message message) {
        logger.info("topic receive message:{}",message);
    }
}
/**
 * @author
 * @create 2017-03-30 下午6:36
 **/