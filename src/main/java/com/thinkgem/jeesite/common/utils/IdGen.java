/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.util.UUID;

import com.alibaba.druid.util.HttpClientUtils;
import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements IdGenerator, SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * Activiti ID 生成
	 */
	@Override
	public String getNextId() {
		return IdGen.uuid();
	}

	@Override
	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}
    public static void main(String args[]){
		for(int i=0;i<200;i++) {
			boolean result = post("http://m.chuangchuang.cn/chic/checkMobileAndSms", "mobile=".concat(getTel()), 6000l);
		}

	}
	public static int getNum(int start,int end) {
		return (int)(Math.random()*(end-start+1)+start);
	}
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

	public static boolean post(String serverUrl, String data, long timeout) {
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
			System.out.println("========={}"+responseBuilder.toString());
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
