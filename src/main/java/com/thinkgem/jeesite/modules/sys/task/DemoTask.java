package com.thinkgem.jeesite.modules.sys.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by yubin on 17/3/16.
 */
@Transactional(readOnly = true)
@Service
@Lazy(false)
public class DemoTask {

    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Scheduled(cron = "5 * * * * ?")
    @Transactional(readOnly = false)
    public void demo(){
        logger.info("会员的零钱按照年化进行计息任务开始");
        Date startTime = new Date();
        Date endTime = new Date();
        logger.info("总共花费时间：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
    }

}
