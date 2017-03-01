package com.cis.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CopyOfServerConn {
	
private static String userName = "root";
    
	private static String password = "zhaolinux";
	
	private static String server = "10.117.129.44";
	
	private static String serverFilePath = "/home/zhaolinux/Downloads/apache-tomcat-7.0.47/RUNNING.txt";
	
	public static Session getServerConnection(){
		JSch sshSingleton = new JSch();
		Session session = null;
		try {
			session = sshSingleton.getSession(userName, server);
		} catch (JSchException e) {
			e.printStackTrace();
		}
		session.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		try {
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		ChannelExec channel = null;
		try {
			channel = (ChannelExec) session.openChannel("exec");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
								channel.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return session;
	}

}
