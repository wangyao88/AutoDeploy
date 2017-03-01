package com.cis.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.cis.deploy.service.DeployService;
import com.cis.deploy.service.impl.DeployServiceImpl;

public class MyTest {
	
	@Test
	public void scanTest(){
//		DeployService deployService = new DeployServiceImpl();
//		boolean result = deployService.isUploadSuccess();
//		System.out.println(result);
	}
	
	@Test
	public void sleepTest(){
		for(int i = 0; i < 8; i++){
			try {
				if(i == 3){
					break;
				}
				TimeUnit.SECONDS.sleep(3);
				System.out.println("------");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
