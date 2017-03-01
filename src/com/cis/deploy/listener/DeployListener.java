package com.cis.deploy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cis.common.util.ServerConnManager;
import com.cis.task.ScheduledMain;

public class DeployListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
		ServerConnManager.clearSessionPool();
	}

	public void contextInitialized(ServletContextEvent event) {
		if(ServerConnManager.initSessionPool()){
			ScheduledMain.startScheduled();
		}
	}

}
