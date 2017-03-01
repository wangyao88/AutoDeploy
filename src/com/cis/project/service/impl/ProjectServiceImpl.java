package com.cis.project.service.impl;

import com.cis.common.util.ServerConnManager;
import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.bean.DeployType;
import com.cis.deploy.manager.DeployManager;
import com.cis.project.service.ProjectService;

public class ProjectServiceImpl implements ProjectService{

	public boolean reConfigurateService(DeployInfo deployInfo) {
		try {
			//停止服务
			stopService(deployInfo);
			//打包服务
			zipService(deployInfo);
			//备份服务
//			backupService(deployInfo);
			//删除服务
			deleteService(deployInfo);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
    private void stopService(DeployInfo deployInfo) throws Exception{
    	if(deployInfo.isApkOrIpa()){
    		return;
    	}
    	Command pidCmd = DeployManager.getInstance().getCommandService().getPIDWithPort(deployInfo.getPort());
		ServerConnManager.executeCommand(deployInfo.getServerType(), pidCmd);
		if(!pidCmd.getResult().isEmpty()){
			String pid = pidCmd.getResult().get(0);
			Command killService = DeployManager.getInstance().getCommandService().getKillServiceWithPID(pid);
			ServerConnManager.executeCommand(deployInfo.getServerType(), killService);
		}
    }
	
    private void zipService(DeployInfo deployInfo) throws Exception{
    	Command command = null;
    	//tomcat download  apk ipa
        if(DeployType.TOMCAT.equals(deployInfo.getDeployType())){
        	//mkdir currentTime
//        	command = DeployManager.getInstance().getCommandService().getCreateCurrentFolder(deployInfo);
//        	ServerConnManager.executeCommand(deployInfo.getServerType(), command);
        	//cp apk ipa 
//        	command = DeployManager.getInstance().getCommandService().getCopyAppFile(deployInfo);
//        	ServerConnManager.executeCommand(deployInfo.getServerType(), command);
        	command = DeployManager.getInstance().getCommandService().getTarApp(deployInfo);
    	}
    	//service bin conf lib
    	if(DeployType.SERVICE.equals(deployInfo.getDeployType())){
    		command = DeployManager.getInstance().getCommandService().getTarService(deployInfo);
    	}
    	//jetty WEB-INF META-INF
    	if(DeployType.JETTY.equals(deployInfo.getDeployType())){
    		command = DeployManager.getInstance().getCommandService().getTarJetty(deployInfo);
    	}
        ServerConnManager.executeCommand(deployInfo.getServerType(), command);
    }
	
//    private void backupService(DeployInfo deployInfo){
//    	
//    }
	
    private void deleteService(DeployInfo deployInfo) throws Exception{
    	Command command = null;
    	//tomcat download  apk ipa
        if(DeployType.TOMCAT.equals(deployInfo.getDeployType())){
        	command = DeployManager.getInstance().getCommandService().getDeleteApp(deployInfo);
    	}
    	//service conf lib
    	if(DeployType.SERVICE.equals(deployInfo.getDeployType())){
    		command = DeployManager.getInstance().getCommandService().getDeleteService(deployInfo);
    	}
    	//jetty WEB-INF META-INF
    	if(DeployType.JETTY.equals(deployInfo.getDeployType())){
    		command = DeployManager.getInstance().getCommandService().getDeleteJetty(deployInfo);
    	}
        ServerConnManager.executeCommand(deployInfo.getServerType(), command);
    }

	public boolean reStartService(DeployInfo deployInfo) {
		if(deployInfo.isApkOrIpa()){
    		return true;
    	}
		Command command = null;
		try {
	    	//service /bin
	    	if(DeployType.SERVICE.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getRestartService(deployInfo);
	    	}
	    	//jetty 
	    	if(DeployType.JETTY.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getRestartJetty(deployInfo);
	    	}
	        ServerConnManager.executeCommand(deployInfo.getServerType(), command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
