package com.cis.tranfer.service;

import com.cis.deploy.bean.DeployInfo;
import com.cis.properties.bean.Property;

public interface TransferService {
	
	public boolean transferFile(DeployInfo deployInfo);

	public boolean transferFileAfterUpdatedProperties(Property property);

}
