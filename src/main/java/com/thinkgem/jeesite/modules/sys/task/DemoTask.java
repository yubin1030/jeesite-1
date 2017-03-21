package com.thinkgem.jeesite.modules.sys.task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    /**
     * 返回手机号码
     */
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    private static String getTel() {
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+thrid;
    }

    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
    @Scheduled(cron = "0 0 0/1 * * ?")
    @Transactional(readOnly = false)
    public void demo(){
        for(int i=0;i<100;i++) {
            logger.info("会员的零钱按照年化进行计息任务开始");
            Date startTime = new Date();

            //boolean result = this.post("http://m.chuangchuang.cn/chic/checkMobileAndSms", "mobile=".concat(getTel()), 6000l);
            //logger.info("result {},", result);
            Date endTime = new Date();
            logger.info("总共花费时间：【" + (endTime.getTime() - startTime.getTime()) / 1000 + "s】");
        }
    }
    public boolean post(String serverUrl, String data, long timeout) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;

        try {
            URL url = new URL(serverUrl);
            URLConnection e = url.openConnection();
            e.setDoOutput(true);
            e.setConnectTimeout(5000);
            wr = new OutputStreamWriter(e.getOutputStream());
            wr.write(data);
            wr.flush();

            reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
            responseBuilder = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                responseBuilder.append(line).append("\n");
            }
             logger.info("========={}"+responseBuilder.toString());
            return true;
        } catch (IOException var22) {
            var22.printStackTrace();
        } finally {
            if(wr != null) {
                try {
                    wr.close();
                } catch (IOException var21) {
                    var21.printStackTrace();
                }
            }

            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException var20) {
                    var20.printStackTrace();
                }
            }

        }

        return false;
    }

}
