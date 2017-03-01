package com.cis.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cis.deploy.bean.Command;
import com.cis.deploy.manager.DeployManager;
import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ServerConnManager {
	
	private static Map<ServerType,Session> sessionPool = new HashMap<ServerType,Session>();
	
	public static boolean initSessionPool(){
		try {
			if(ServerConnManager.sessionPool.isEmpty()){
				Server zookeeper = DeployManager.getInstance().getServerService().getServerByType(ServerType.ZOOKEEPER);
				Server nginx = DeployManager.getInstance().getServerService().getServerByType(ServerType.NGINX);
				Session zSeesion = ServerConnManager.getSession(zookeeper);
				Session nSeesion = ServerConnManager.getSession(nginx);
				sessionPool.put(ServerType.ZOOKEEPER, zSeesion);
				sessionPool.put(ServerType.NGINX, nSeesion);
				System.out.println("初始化session连接池成功!");
			}
		} catch (Exception e) {
			System.out.println("初始化session连接池出错!"+e.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	public static void clearSessionPool(){
		try {
			if(!ServerConnManager.sessionPool.isEmpty()){
				for(Map.Entry<ServerType,Session> map : sessionPool.entrySet()){
					Session seesion = map.getValue();
					if(seesion != null){
						seesion.disconnect();
					}
				}
				System.out.println("清空session连接池成功!");
			}
		} catch (Exception e) {
			System.out.println("清空session连接池出错!"+e.getLocalizedMessage());
		}
	}
	
	public static Session getSession(Server server){
		Session session = null;
		try {
			JSch sshSingleton = new JSch();
			System.out.println("开始获取session连接");
			System.out.println(server);
			session = sshSingleton.getSession(server.getUserName(), server.getHost());
			session.setPassword(server.getPassword());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
		} catch (Exception e) {
			System.out.println("获取session连接失败!"+e.getLocalizedMessage());
		}
		System.out.println("获取session连接成功");
		return session;
	}
	
	public static List<String> executeCommand(ServerType serverType,Command command){
		if(command == null){
			return null;
		}
		Session session = ServerConnManager.sessionPool.get(serverType);
		List<String> result = new ArrayList<String>();
		ChannelExec channel = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		try {
			channel = (ChannelExec) session.openChannel("exec");
			inputStreamReader = new InputStreamReader(channel.getInputStream());
			bufferedReader = new BufferedReader(inputStreamReader);
			channel.setCommand(command.getCommand());
			channel.connect();
			String msg;
			while ((msg = bufferedReader.readLine()) != null) {
				result.add(msg);
			}
			channel.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(inputStreamReader != null){
					inputStreamReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(bufferedReader != null){
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(channel != null){
				channel.disconnect();
			}
		}
		command.setResult(result);
		return result;
	}
	
	public static Command executeCommand(Server server,Command command){
		Session session = null;
		ChannelExec channel = null;
		try {
			JSch sshSingleton = new JSch();
			session = sshSingleton.getSession(server.getUserName(), server.getHost());
			session.setPassword(server.getPassword());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = (ChannelExec) session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			channel.setCommand(command.getCommand());
			channel.connect();
			String msg;
			List<String> result = new ArrayList<String>();
			while ((msg = in.readLine()) != null) {
				result.add(msg);
			}
			command.setResult(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(channel != null){
				channel.disconnect();
		    }
		    if(session != null){
		    	session.disconnect();
		    }
		}
		return command;
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.setUserName("wangyao");
		server.setPassword("wangyao");
		server.setHost("192.168.25.129");
		Command command = new Command();
		command.setCommand("md5sum /home/wangyao/program/python/*.tar.gz");
		executeCommand(server,command);
		List<String> result = command.getResult();
		for(int i = 0; i < result.size(); i++){
			System.out.println(result.get(i));
		}
	}

}
