package com.wizard.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshUtil {

	private static final Logger logger = LoggerFactory.getLogger(SshUtil.class);
	
	private static Session session;
	
	public static Session getInstance() {
		
		if(session == null || !session.isConnected()) {
			synchronized (SshUtil.class) {
				if(session == null || !session.isConnected()) {
					session = getSession();
				}
			}
		}
		
        return session;
    }
	
	private SshUtil() {}


	private static Session getSession() {
		try {

			JSch jsch = new JSch();
			
			logger.info("begin connect");
			
			Session session = jsch.getSession(Sensitive.USERNAME, Sensitive.IP, Sensitive.PORT);
			session.setPassword(Sensitive.PASSWORD); // 设置密码
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");// 在代码里需要跳过检测。否则会报错找不到主机
			session.setConfig(config); // 为Session对象设置properties
			int timeout = 30000;
			session.setTimeout(timeout); // 设置timeout时间
			session.connect();
			
			logger.info("connect success");
			
			return session;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	public static String execCommand(String command) {

		ChannelExec channelExec = null;
		StringBuilder sb = new StringBuilder();

		Session session = null;

		try {

			session = getInstance();

			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(command); // 添加传入进来的shell命令
			channelExec.setErrStream(System.err);
			channelExec.connect();
			logger.info("start execute channel command! {}", command);
			BufferedReader in = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
			String msg;
			while ((msg = in.readLine()) != null) {
				sb.append(msg);
				sb.append("\r\n");
			}
			in.close();
			channelExec.disconnect();
			logger.info("end execute channel command! {}", command);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (channelExec != null) {
				channelExec.disconnect();
			}
		}

		return sb.toString();
	}

}
