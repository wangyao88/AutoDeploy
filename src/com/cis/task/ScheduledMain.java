package com.cis.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cis.common.bean.Constants;

public class ScheduledMain {
	
	public static void startScheduled(){
		ScanTask scanTask = ScanTask.getInstance();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(scanTask, Constants.INITIALDELAY_IN_MINUTES, Constants.RUNTASK_PERIOD_IN_MINUTES, TimeUnit.MINUTES);
	}

	public static void main(String[] args) {
		startScheduled();
	}
}
