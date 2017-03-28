package com.thinkgem.jeesite.modules.sys.task;


import com.thinkgem.jeesite.consumer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@Transactional(readOnly = true)
@Service
@Lazy(false)
public class DemoTask {

    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    MessageProducer messageProducer;
    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional(readOnly = false)
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


}
