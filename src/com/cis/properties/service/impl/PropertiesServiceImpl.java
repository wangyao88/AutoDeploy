package com.cis.properties.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.cis.common.util.ServerConnManager;
import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.bean.DeployType;
import com.cis.deploy.manager.DeployManager;
import com.cis.properties.service.PropertiesService;

public class PropertiesServiceImpl implements PropertiesService{

	public boolean replaceAllProperties(DeployInfo deployInfo) {
		if(deployInfo.isApkOrIpa()){
    		return true;
    	}
		Command command = null;
		try {
			//service conf *
	    	if(DeployType.SERVICE.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getReplaceServiceProperties(deployInfo);
	    	}
	    	//jetty WEB-INF classes *.properties *.xml *.conf
	    	if(DeployType.JETTY.equals(deployInfo.getDeployType())){
	    		command = DeployManager.getInstance().getCommandService().getReplaceJettyProperties(deployInfo);
	    	}
	        ServerConnManager.executeCommand(deployInfo.getServerType(), command);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String readContents(String path) {
		String centents = "";
		try {
			centents = readFile(path);
		} catch (Exception e) {
			centents = "读取配置文件失败!"+e.getLocalizedMessage();
		}
		return centents;
	}
	
	private String readFile(String filePath){
		StringBuilder centents = new StringBuilder();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					centents.append(lineTxt+"\n");
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
        return centents.toString();
    }

}
