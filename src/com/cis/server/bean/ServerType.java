package com.cis.server.bean;

public enum ServerType {
	
	ZOOKEEPER,NGINX;

	public static ServerType getServerType(String key) {
		if(key.contains("zookeeper")){
			return ZOOKEEPER;
		}
		if(key.contains("nginx")){
			return NGINX;
		}
		if(key.contains("172.20.0.11")){
			return ZOOKEEPER;
		}
		if(key.contains("172.30.0.10")){
			return NGINX;
		}
		return null;
	}

}
