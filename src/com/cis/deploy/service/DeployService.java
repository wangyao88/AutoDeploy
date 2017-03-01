package com.cis.deploy.service;

import java.util.List;

import com.cis.deploy.bean.DeployInfo;

public interface DeployService {
	
	public void deploy();
	
	public DeployInfo getDeployInfoWithFileName(String fileName);

}
