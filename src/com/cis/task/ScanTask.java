package com.cis.task;

import lombok.extern.java.Log;

import com.cis.deploy.service.DeployService;
import com.cis.deploy.service.impl.DeployServiceImpl;

@Log
public class ScanTask implements Runnable{
	
	private DeployService service = null;
	private boolean isDeploying = false;
	
	private ScanTask(){
		service = new DeployServiceImpl();
	}
	
	public static ScanTask getInstance(){
		return new ScanTask();
	}
	
	public void run() {
		log.info("开始本次自动部署任务");
		if(isDeploying){
			return;
		}
		isDeploying = true;
		service.deploy();
		isDeploying = false;
		log.info("结束本次自动部署任务");
	}

}
