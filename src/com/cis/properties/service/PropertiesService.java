package com.cis.properties.service;

import com.cis.deploy.bean.DeployInfo;


public interface PropertiesService {
	
	public boolean replaceAllProperties(DeployInfo deployInfo);

	public String readContents(String path);

}
