package com.cis.common.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class ReadRemoteFile {
	
	private static String userName = "wangyao";
    
	private static String password = "wangyao";
	
	private static String server = "192.168.25.129";
	
	private static String serverFilePath = "/home/zhaolinux/Downloads/apache-tomcat-7.0.47/RUNNING.txt";
	
	public static void main(String[] args) throws Exception {

		JSch sshSingleton = new JSch();
		Session session = sshSingleton.getSession(userName, server);
		session.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.connect();
		 //打开执行管道
		ChannelExec channel = (ChannelExec) session.openChannel("exec");
		BufferedReader in = new BufferedReader(new InputStreamReader(
							channel.getInputStream()));
		//channel.setCommand("cp /home/zhaolinux/Downloads/apache-tomcat-7.0.47/RUNNING.txt /home/zhaolinux/Downloads/");  
		//channel.setCommand("md5sum /home/wangyao/program/python/*.tar.gz");
		channel.setCommand("scp -r /home/wangyao/program/python/*.tar.gz root@172.30.0.10:/home/adf");
		channel.connect();
		String msg;
		while ((msg = in.readLine()) != null) {
			System.out.println(msg);
		}
		session.disconnect();
	}
}
