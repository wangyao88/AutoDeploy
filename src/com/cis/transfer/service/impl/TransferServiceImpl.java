package com.cis.transfer.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cis.common.util.ServerConnManager;
import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.bean.DeployType;
import com.cis.deploy.manager.DeployManager;
import com.cis.properties.bean.Property;
import com.cis.server.bean.ServerType;
import com.cis.tranfer.service.TransferService;

public class TransferServiceImpl implements TransferService{

	public boolean transferFile(DeployInfo deployInfo) {
		Command command = null;
		try {
			//tomcat download  apk ipa
	        if(DeployType.TOMCAT.equals(deployInfo.getDeployType())){
	        	command = DeployManager.getInstance().getCommandService().getTransferAppToTomcat(deployInfo);
	    	}
	    	//service conf lib
	    	if(DeployType.SERVICE.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getTransferServiceToFile(deployInfo);
	    	}
	    	//jetty WEB-INF META-INF
	    	if(DeployType.JETTY.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getTransferProjectToJettyt(deployInfo);
	    	}
	        ServerConnManager.executeCommand(deployInfo.getServerType(), command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean transferFileAfterUpdatedProperties(Property property) {
		List<Command> commands = new ArrayList<Command>();
		try {
			ServerType serverType = ServerType.getServerType(property.getFilePath());
			Command mkdirCommand = DeployManager.getInstance().getCommandService().getMkdirBeforeCopyFile(property);
			commands.add(mkdirCommand);
			Command copyCommand = null;
			if(serverType.equals(ServerType.ZOOKEEPER)){
				copyCommand = DeployManager.getInstance().getCommandService().getCopyFile(property);
			}
			if(serverType.equals(ServerType.NGINX)){
				copyCommand = DeployManager.getInstance().getCommandService().getSCopyFile(property);
			}
			if(copyCommand != null){
				commands.add(copyCommand);
			}
			ServerConnManager.batchExecuteCommandsInSameServerType(serverType, commands);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
