package com.cis.server.service.impl;

import java.util.Map;

import com.cis.common.util.PropertyUtil;
import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;
import com.cis.server.service.ServerService;

public class ServerServiceImpl implements ServerService{

	public Server getZookeeperServer() {
		Map<String,String> map = PropertyUtil.getPropertiesAllValue();
		Server server = new Server();
		server.setHost(map.get("zookeeper.host"));
		server.setPassword(map.get("zookeeper.password"));
		server.setUserName(map.get("zookeeper.userName"));
		return server;
	}

	public Server getNginxServer() {
		Map<String,String> map = PropertyUtil.getPropertiesAllValue();
		Server server = new Server();
		server.setHost(map.get("nginx.host"));
		server.setPassword(map.get("nginx.password"));
		server.setUserName(map.get("nginx.userName"));
		return server;
	}

	public String getScanPath() {
		Map<String,String> map = PropertyUtil.getPropertiesAllValue();
		return map.get("scanPath");
	}

	public String getMD5TXTPath() {
		Map<String,String> map = PropertyUtil.getPropertiesAllValue();
		return map.get("scanTXTPath");
	}

	public Server getServerByType(ServerType serverType) {
		if(serverType.equals(ServerType.ZOOKEEPER)){
			return getZookeeperServer();
		}
		if(serverType.equals(ServerType.NGINX)){
			return getNginxServer();
		}
		return null;
	}

}
