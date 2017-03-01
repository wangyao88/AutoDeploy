package com.cis.project.service;

import com.cis.deploy.bean.DeployInfo;

public interface ProjectService {
	
	public boolean reConfigurateService(DeployInfo deployInfo);

	public boolean reStartService(DeployInfo deployInfo);
}
