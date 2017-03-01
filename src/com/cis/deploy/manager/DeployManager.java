package com.cis.deploy.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.cis.command.service.CommandService;
import com.cis.command.service.impl.CommandServiceImpl;
import com.cis.common.util.PropertyUtil;
import com.cis.decompress.service.DecompressService;
import com.cis.decompress.service.impl.DecompressServiceImpl;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.service.DeployService;
import com.cis.deploy.service.impl.DeployServiceImpl;
import com.cis.project.service.ProjectService;
import com.cis.project.service.impl.ProjectServiceImpl;
import com.cis.properties.service.PropertiesService;
import com.cis.properties.service.impl.PropertiesServiceImpl;
import com.cis.scan.service.ScanService;
import com.cis.scan.service.impl.ScanServiceImpl;
import com.cis.server.service.ServerService;
import com.cis.server.service.impl.ServerServiceImpl;
import com.cis.tranfer.service.TransferService;
import com.cis.transfer.service.impl.TransferServiceImpl;

@Data
public class DeployManager {

	private DecompressService decompressService = new DecompressServiceImpl();
	private ProjectService projectService = new ProjectServiceImpl();
	private PropertiesService propertiesService = new PropertiesServiceImpl();
	private ScanService scanService = new ScanServiceImpl();
	private CommandService commandService = new CommandServiceImpl();
	private ServerService serverService = new ServerServiceImpl(); 
	private DeployService deployService = new DeployServiceImpl();
	private TransferService transferService = new TransferServiceImpl();
	private Map<String,String> propertiesValue = PropertyUtil.getPropertiesAllValue();
	public static List<String> uploadFiles = new ArrayList<String>();
	private boolean scanSuccess;
	private boolean decompressFileSuccess;
	private boolean reConfigurateServiceSuccess;
	private boolean replaceAllPropertiesSuccess;
	private boolean reStartServiceSuccess;
	private boolean transferServiceSuccess;
	
	private DeployManager(){
		
	} 
	
	public static DeployManager getInstance(){
		return Singleton.deployManager;
	}
	
	private static class Singleton{
		private static final DeployManager deployManager = new DeployManager();
	}

	public void deploy() {
		//1 扫描上传文件夹
		System.out.println("开始扫描部署文件");
		scan();
		for(String fileName : uploadFiles){
			DeployInfo deployInfo = deployService.getDeployInfoWithFileName(fileName);
			//2 解压包 放到临时位置
			decompressFile(deployInfo);
			//3 操作(停止、打包、删除)服务
			reConfigurateService(deployInfo);
			//4 配置文件替换
			replaceAllProperties(deployInfo);
			//5 将服务放到指定位置
			transferService(deployInfo);
			//6 重启服务
			reStartService(deployInfo);
		}
	}
	
	private void scan() {
		this.setScanSuccess(scanService.scan());
	}

	private void decompressFile(DeployInfo deployInfo) {
		if (this.isScanSuccess()) {
			System.out.println("开始解压部署文件");
			this.setDecompressFileSuccess(decompressService.decompressFile(deployInfo));
		}
	}

	private void reConfigurateService(DeployInfo deployInfo) {
		if (this.isDecompressFileSuccess()) {
			System.out.println("开始操作服务");
			this.setReConfigurateServiceSuccess(projectService.reConfigurateService(deployInfo));
		}
	}

	private void replaceAllProperties(DeployInfo deployInfo) {
		if (this.isReConfigurateServiceSuccess()) {
			System.out.println("开始替换配置文件");
			this.setReplaceAllPropertiesSuccess(propertiesService.replaceAllProperties(deployInfo));
		}
	}
	
	private void transferService(DeployInfo deployInfo) {
		if (this.isReplaceAllPropertiesSuccess()) {
			System.out.println("开始将部署文件放到指定位置");
			this.setTransferServiceSuccess(transferService.transferFile(deployInfo));
		}
	}

	private void reStartService(DeployInfo deployInfo) {
		if (this.isTransferServiceSuccess()) {
			System.out.println("开始重启服务");
			this.setReStartServiceSuccess(projectService.reStartService(deployInfo));
		}
	}

}
