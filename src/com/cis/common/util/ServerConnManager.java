package com.cis.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import lombok.extern.java.Log;

import com.cis.deploy.bean.Command;
import com.cis.deploy.manager.DeployManager;
import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Log
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
				log.info("初始化session连接池成功!");
			}
		} catch (Exception e) {
			log.info("初始化session连接池出错!"+e.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	private static boolean initSessionPoolByServerType(ServerType serverType){
		try {
			Server server = DeployManager.getInstance().getServerService().getServerByType(serverType);
			Session session = ServerConnManager.getSession(server);
			sessionPool.put(serverType, session);
			log.info("初始化"+serverType+"session连接池成功!");
		} catch (Exception e) {
			log.info("初始化"+serverType+"session连接池出错!"+e.getLocalizedMessage());
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
				log.info("清空session连接池成功!");
			}
		} catch (Exception e) {
			log.info("清空session连接池出错!"+e.getLocalizedMessage());
		}
	}
	
	public static Session getSession(Server server){
		Session session = null;
		try {
			JSch sshSingleton = new JSch();
			log.info("开始获取session连接");
			log.info(server.toString());
			session = sshSingleton.getSession(server.getUserName(), server.getHost());
			session.setPassword(server.getPassword());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
		} catch (Exception e) {
			log.info("获取session连接失败!"+e.getLocalizedMessage());
		}
		log.info("获取session连接成功");
		return session;
	}
	
	public static List<String> executeCommand(ServerType serverType,Command command){
		if(command == null){
			return null;
		}
		Session session = ServerConnManager.sessionPool.get(serverType);
		if(session == null || !session.isConnected()){
			initSessionPoolByServerType(serverType);
		}
		session = ServerConnManager.sessionPool.get(serverType);
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
			log.info("执行命令: " + command.getCommand());
			String msg;
			while ((msg = bufferedReader.readLine()) != null) {
				result.add(msg);
			}
		} catch (Exception e) {
			log.info("执行命令["+command.getCommand()+"]失败。失败原因:"+e.getLocalizedMessage());
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
	
	public static void batchExecuteCommandsInSameServerType(ServerType serverType,List<Command> commands) {
		for(Command command : commands){
			executeCommand(serverType,command);
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.setUserName("root");
		server.setPassword("wangyao");
		server.setHost("192.168.25.130");
		Command command = new Command();
		command.setCommand("/home/osp/jettystart.sh");//  pwd
		executeCommand(server,command);
		List<String> result = command.getResult();
		for(int i = 0; i < result.size(); i++){
			System.out.println(result.get(i));
		}
	}
	
}
