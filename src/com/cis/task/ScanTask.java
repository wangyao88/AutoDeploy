package com.cis.task;

import com.cis.deploy.service.DeployService;
import com.cis.deploy.service.impl.DeployServiceImpl;

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
		System.out.println("开始本次自动部署任务");
		if(isDeploying){
			return;
		}
		isDeploying = true;
		service.deploy();
		isDeploying = false;
		System.out.println("结束本次自动部署任务");
	}

}
