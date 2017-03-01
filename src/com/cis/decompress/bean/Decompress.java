package com.cis.decompress.bean;

import lombok.Data;

import com.cis.deploy.bean.DeployType;
import com.cis.server.bean.ServerType;

@Data
public class Decompress {
	
	private ServerType serverType;
	private String path;
	private DeployType deployType;

}
