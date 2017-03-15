package com.cis.decompress.service.impl;

import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;

import com.cis.common.bean.Constants;
import com.cis.common.util.ServerConnManager;
import com.cis.decompress.service.DecompressService;
import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.manager.DeployManager;
import com.cis.server.bean.ServerType;

@Log
public class DecompressServiceImpl implements DecompressService{

	public boolean decompressFile(DeployInfo deployInfo) {
		try {
			if(deployInfo.getServerType().equals(ServerType.NGINX)){
				tansferFile(deployInfo);
			}
			if(deployInfo.getServerType().equals(ServerType.ZOOKEEPER)){
				copyFile(deployInfo);
			}
			//检查文件是否已经传输或复制完成 每5秒检查一次
			for(int i = 0; i < Constants.CHECK_TRANSFER_NUM; i++){
				if(checkFileMD5Value(deployInfo)){
					tarFile(deployInfo);
					break;
				}
				if(i == Constants.CHECK_TRANSFER_NUM-1){
					log.info("文件传输失败");
					return false;
				}
				TimeUnit.SECONDS.sleep(Constants.CHECK_TRANSFER_PERIOD_IN_SECOND);
			}
			return true;
		} catch (Exception e) {
			log.info("解压文件出错");
			e.printStackTrace();
			return false;
		}
	}

	private boolean checkFileMD5Value(DeployInfo deployInfo) {
		StringBuilder path = new StringBuilder();
		path.append(DeployManager.getInstance().getPropertiesValue().get(Constants.DEPLOY_TEMP_PATH));
		path.append(Constants.SLASH);
		path.append(getFileName(deployInfo.getFileName()));
		Command command = DeployManager.getInstance().getCommandService().getMD5Command(path.toString());
		ServerConnManager.executeCommand(deployInfo.getServerType(), command);
		if(command.getResult().isEmpty()){
			return false;
		}
		if(!command.getResult().get(0).split(Constants.SPLIT)[0].equals(deployInfo.getMd5Value())){
			return false;
		}
		return true;
	}

	private void copyFile(DeployInfo deployInfo) {
		Command command = DeployManager.getInstance().getCommandService().getCopyFile(getFileName(deployInfo.getFileName()));
		ServerConnManager.executeCommand(deployInfo.getServerType(), command);
	}

	private void tansferFile(DeployInfo deployInfo) {
		Command command = DeployManager.getInstance().getCommandService().getTransferFile(getFileName(deployInfo.getFileName()));
		ServerConnManager.executeCommand(deployInfo.getServerType(), command);
	}

	private void tarFile(DeployInfo deployInfo) {
		Command command = DeployManager.getInstance().getCommandService().getTarFile(getFileName(deployInfo.getFileName()));
		ServerConnManager.executeCommand(deployInfo.getServerType(), command);
	}
	
	private String getFileName(String fileName) {
		if(!fileName.contains(Constants.IPA) && !fileName.contains(Constants.APK)){
			fileName += Constants.COMPRESSED_PACKET_EXTENSION_IN_LINUX;
		}
		return fileName;
	}
}
