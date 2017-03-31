package com.thinkgem.jeesite.modules;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by yubin on 17/3/31.
 */
public class TestRedis {
    private Jedis jedis;

      @Before
      public void setup() {
          //连接redis服务器，192.168.0.100:6379
          jedis = new Jedis("118.89.102.50", 6379);

      }
      @Test
      public void testString(){
          System.out.println(jedis.ping());
      }

}
/**
 * @author
 * @create 2017-03-31 上午11:59
 **/