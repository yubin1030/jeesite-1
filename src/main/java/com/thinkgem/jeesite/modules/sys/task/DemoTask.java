package com.thinkgem.jeesite.modules.sys.task;

import com.thinkgem.jeesite.common.utils.JedisUtils;
import org.springframework.amqp.core.MessageProperties;
import com.thinkgem.jeesite.consumer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;


/**
 * Created by yubin on 17/3/16.
 */
@Service
@Lazy(false)
public class DemoTask {

    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    MessageProducer messageProducer;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @Scheduled(cron = "0/10 * * * * ?")
    public void testRedisTemplate(){
        logger.info("=======");
        String key="yubin";
        String value="yubin";
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection)  {
                connection.set(key.getBytes(), value.getBytes());
                connection.expire(key.getBytes(), 2);

                return 1L;
            }
        });
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void demo(){
        logger.info("===========");
        int a = Integer.MAX_VALUE;
        while (a > 0) {
            messageProducer.sendMessage("Hello, I am amq sender num :" + a--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    @Scheduled(cron = "0/30 * * * * ?")
    public  void fanoutMessage(){
        //往名字为leo.pay.fanout.exchange的路由里面发送数据，客户端中只要是与该路由绑定在一起的队列都会收到相关消息，
        // 这类似全频广播，发送端不管队列是谁，都由客户端自己去绑定，谁需要数据谁去绑定自己的处理队列。
        for(int i = 1; i <= 10; i++) {
            String str = "hello" + i;
            rabbitTemplate.send("leo.pay.fanout.exchange", "", new Message(str.getBytes(), new MessageProperties()));
        }
    }
    @Scheduled(cron = "0/50 * * * * ?")
    public  void topicMessage(){
        //第二个参数为路由key(routingKey)的值，当路由可以为test321.hello.test123时，两个消费队列都可以收到消息，当值为test321.hello.aaa时，
        // 只有绑定了test321.#的队列才可以收到消息，当值为ta1.hello.test123，只有绑定了*.*.test123的队列才可收到消息
        for(int i = 1; i <= 10; i++) {
            String str = "hello" + i;
            rabbitTemplate.send("leo.pay.topic.exchange", "test321.hello.test123", new Message(str.getBytes(), new MessageProperties()));
        }
    }



}
