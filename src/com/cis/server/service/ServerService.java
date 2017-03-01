package com.cis.server.service;

import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;

public interface ServerService {
	
	/*
	 * zookeeper 
	 * dubbo-monitor-simple
	 * jetty osp
	 * jetty uum
	 * service
	 */
	public Server getZookeeperServer();
	
	/**
	 * jetty mi-api-nhl-membe
	 * jetty mi-api-nhl-smart
	 * jetty mi-web-smart
	 * Nginx
	 * apache-tomcat-7.0.4 app-download
	 * monitor-tomcat
	 * @return
	 */
	public Server getNginxServer();
	
	/**
	 * 获取扫描路径 每十分钟扫描一次
	 * @return
	 */
	public String getScanPath();

	/**
	 * 获取扫描路径下的md5文件
	 * @return
	 */
	public String getMD5TXTPath();

	public Server getServerByType(ServerType serverType);
	

}
