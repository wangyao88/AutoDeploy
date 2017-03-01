package com.cis.deploy.bean;

import lombok.Data;

import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;

@Data
public class DeployInfo {
	
	private Server server;
	private ServerType serverType;
    //部署路径
	private String path;
	private DeployType deployType;
	private String fileName;
	//存放待解压文件
	private String tempPath;
	private String md5Value;
	private boolean isApkOrIpa;
	private String port;

}
