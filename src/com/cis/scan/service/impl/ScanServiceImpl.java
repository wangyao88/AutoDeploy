package com.cis.scan.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import org.apache.commons.lang3.StringUtils;

import com.cis.common.bean.Constants;
import com.cis.common.util.ServerConnManager;
import com.cis.deploy.bean.Command;
import com.cis.deploy.manager.DeployManager;
import com.cis.scan.service.ScanService;
import com.cis.server.bean.ServerType;

@Log
public class ScanServiceImpl implements ScanService{

	public boolean isUploadSuccess() {
		List<String> contents = getMD5TXTContent();
		if(contents.isEmpty()){
			log.info("无上传文件");
			return false;
		}
		setUploadFiles(contents);
		List<String> md5InDir = getMD5InDir();
		int contentsSize = contents.size();
		int md5InDirSize = md5InDir.size();
		int count = 0;
		for(int i = 0; i < contentsSize; i++){
			String content = contents.get(i);
			if(StringUtils.isEmpty(content)){
				break;
			}
			for(int j = 0; j < md5InDirSize; j++){
				String fileMD5 = md5InDir.get(j);
				if(StringUtils.isEmpty(fileMD5)){
					break;
				}
				if(content.split(Constants.SPLIT)[0].equals(fileMD5.split(Constants.SPLIT)[0])){
					System.out.println(content.split(Constants.SPLIT)[0] + "--" + fileMD5.split(Constants.SPLIT)[0]);
					count++;
				}
			}
		}
		return count == contentsSize;
	}

	private void setUploadFiles(List<String> contents) {
//		List<String> uploadFiles = new ArrayList<String>();
//		for(String file : contents){
//			uploadFiles.add(file.split(Constants.SPLIT)[1]);
//		}
		DeployManager.uploadFiles = contents;
	}

	private List<String> getMD5InDir() {
		String path = DeployManager.getInstance().getServerService().getScanPath()+"/*";
		return getMD5InDir(path);
	}

	private List<String> getMD5TXTContent() {
		String fileName = DeployManager.getInstance().getServerService().getMD5TXTPath();
		return getMD5TXTContent(fileName);
	}

	public List<String> getMD5TXTContent(String fileName) {
//		Server server = serverService.getZookeeperServer();
		Command command = DeployManager.getInstance().getCommandService().getFileContent(fileName);
		ServerConnManager.executeCommand(ServerType.ZOOKEEPER, command);
		return command.getResult();
	}

	public List<String> getMD5InDir(String path) {
//		Server server = serverService.getZookeeperServer();
		Command command = DeployManager.getInstance().getCommandService().getMD5Command(path);
		ServerConnManager.executeCommand(ServerType.ZOOKEEPER, command);
		return command.getResult();
	}

	public void clearUploadDir() {
		log.info("开始删除上传文件");
		String path = DeployManager.getInstance().getServerService().getScanPath()+"/*";
		Command command = DeployManager.getInstance().getCommandService().getClearUploadDirCommand(path);
		ServerConnManager.executeCommand(ServerType.ZOOKEEPER, command);
	}

	public boolean scan() {
		boolean isUploadSuccess = false;
		//每隔一分钟扫描一次  扫八次
		for(int i = 0; i < 8; i++){
		    isUploadSuccess = isUploadSuccess();
			if(isUploadSuccess){
				log.info("上传成功");
				break;
			}
			log.info("尚未上传成功");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(!isUploadSuccess){
			//上传失败 清空文件夹
			log.info("上传失败");
			clearUploadDir();
		}
		return isUploadSuccess;
	}

	public void deleteAllTempFlie() {
		try {
			log.info("30秒后开始删除临时文件");
			TimeUnit.SECONDS.sleep(30);
			List<Command> zookeeperCommands = new ArrayList<Command>();
			List<Command> nginxCommands = new ArrayList<Command>();
			Command command = getDeleteTempFileCommand("scanPath");
			zookeeperCommands.add(command);
			command = getDeleteTempFileCommand("deploy.tempPath");
			zookeeperCommands.add(command);
			nginxCommands.add(command);
		    command = getDeleteTempFileCommand("deploy.propertiesPath");
			zookeeperCommands.add(command);
			nginxCommands.add(command);
			ServerConnManager.batchExecuteCommandsInSameServerType(ServerType.ZOOKEEPER, zookeeperCommands);
			ServerConnManager.batchExecuteCommandsInSameServerType(ServerType.NGINX, nginxCommands);
		} catch (Exception e) {
			log.info("删除临时文件失败");
			e.printStackTrace();
		}
	}

	private Command getDeleteTempFileCommand(String key) {
		String path = DeployManager.getInstance().getPropertiesValue().get(key)+"/*";
		Command command = DeployManager.getInstance().getCommandService().getDeleteFiles(path);
		return command;
	}

}
