package com.cis.deploy.service.impl;

import java.util.Map;

import com.cis.common.bean.Constants;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.bean.DeployType;
import com.cis.deploy.manager.DeployManager;
import com.cis.deploy.service.DeployService;
import com.cis.server.bean.Server;
import com.cis.server.bean.ServerType;

public class DeployServiceImpl implements DeployService {

	public void deploy() {
		DeployManager.getInstance().deploy();
	}
	
	/**
	 * fileName格式   (md5Value  fileName)
	 */
	public DeployInfo getDeployInfoWithFileName(String fileName) {
		String realFileName = fileName.split(Constants.SPLIT)[1];
		realFileName = realFileName.replaceAll(Constants.COMPRESSED_PACKET_EXTENSION_IN_LINUX, Constants.STRING_NULL);
		String md5Value = fileName.split(Constants.SPLIT)[0];
		DeployInfo deployInfo = new DeployInfo(); 
		Map<String,String> propertiesValue = DeployManager.getInstance().getPropertiesValue();
		for(Map.Entry<String, String> entry : propertiesValue.entrySet()){
			String key = entry.getKey();
			String value = entry.getValue();
			if(key.contains(realFileName) && !key.contains(Constants.PORT)){
				deployInfo.setFileName(realFileName);
				deployInfo.setPath(value);
				deployInfo.setServerType(ServerType.getServerType(key));
				Server server = DeployManager.getInstance().getServerService().getServerByType(ServerType.getServerType(key));
				deployInfo.setServer(server);
				deployInfo.setDeployType(DeployType.getDeployType(value));
				deployInfo.setTempPath(propertiesValue.get(Constants.DEPLOY_TEMP_PATH));
				deployInfo.setMd5Value(md5Value);
				if(realFileName.contains(Constants.APK)||realFileName.contains(Constants.IPA)){
					deployInfo.setApkOrIpa(true);
				}else{
					deployInfo.setApkOrIpa(false);
					deployInfo.setPort(propertiesValue.get(key+Constants.KEY_PORT));
				}
				break;
			}
		}
		return deployInfo;
	}
}
