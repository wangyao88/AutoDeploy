package com.cis.command.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cis.command.service.CommandService;
import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.deploy.bean.DeployType;
import com.cis.deploy.manager.DeployManager;
import com.cis.properties.bean.Property;

public class CommandServiceImpl implements CommandService{
	
	private Map<String,String> map;

	private Map<String,String> getMap(){
		if(map == null){
			map = DeployManager.getInstance().getPropertiesValue();
		}
	    return map;
	}
	
	public Command getMD5Command(String path) {
		Command command = new Command();
		command.setCommand("md5sum "+path);
		return command;
	}

	public Command getFileContent(String fileName) {
		Command command = new Command();
		command.setCommand("cat "+fileName);
		return command;
	}

	public Command getClearUploadDirCommand(String scanPath) {
		Command command = new Command();
		command.setCommand("rm -f "+scanPath);
		return command;
	}

	public Command getTransferFile(DeployInfo deployInfo) {
		//scp -r /home/cis-service/mi-web-smart.tar.gz root@172.30.0.10:/home/tempPath
		StringBuilder cmd = new StringBuilder();
		cmd.append("scp -r ");
		cmd.append(getMap().get("scanPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append(" ");
		cmd.append(getMap().get("nginx.userName"));
		cmd.append("@");
		cmd.append(getMap().get("nginx.host"));
		cmd.append(":");
		cmd.append(getMap().get("deploy.tempPath"));
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	
	public Command getTransferFile(String path) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("scp -r ");
		cmd.append(getMap().get("scanPath"));
		cmd.append("/");
		cmd.append(path);
		cmd.append(" ");
		cmd.append(getMap().get("nginx.userName"));
		cmd.append("@");
		cmd.append(getMap().get("nginx.host"));
		cmd.append(":");
		cmd.append(getMap().get("deploy.tempPath"));
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getCopyFile(DeployInfo deployInfo) {
		//cp /home/cis-service/mi-web-smart.tar.gz /home/tempPath
		StringBuilder cmd = new StringBuilder();
		cmd.append("cp ");
		cmd.append(getMap().get("scanPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append(" ");
		cmd.append(getMap().get("deploy.tempPath"));
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	
	public Command getCopyFile(String  fileName) {
		//cp /home/cis-service/mi-web-smart.tar.gz /home/tempPath
		StringBuilder cmd = new StringBuilder();
		cmd.append("cp ");
		cmd.append(getMap().get("scanPath"));
		cmd.append("/");
		cmd.append(fileName);
		cmd.append(" ");
		cmd.append(getMap().get("deploy.tempPath"));
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTarFile(DeployInfo deployInfo) {
		//tar -zxvf /home/tempPath/mi-web-smart.tar.gz
		StringBuilder cmd = new StringBuilder();
		cmd.append("tar -zxvf ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	
	public Command getTarFile(String fileName) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("tar -zxvf ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(fileName);
		cmd.append(" ");
		cmd.append("-C ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getStopService(DeployInfo deployInfo) {
		//
		return null;
	}

	public Command getPIDWithPort(String port) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("lsof -t -i:");
		cmd.append(port);
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getKillServiceWithPID(String pid) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("kill -9 ");
		cmd.append(pid);
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	public Command getTarService(DeployInfo deployInfo){
		//tar -zcvf /home/wangyao/program/python/src/my.2017_02_22_14_55.tar.gz /home/wangyao/program/python/src/file1 /home/wangyao/program/python/src/file2
		StringBuilder cmd = new StringBuilder();
		cmd.append("tar -zcvf ");
		cmd.append(getTarServiceFileName(deployInfo));
		cmd.append(deployInfo.getPath()+"/bin ");
		cmd.append(deployInfo.getPath()+"/conf ");
		cmd.append(deployInfo.getPath()+"/lib ");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	
	private String getTarServiceFileName(DeployInfo deployInfo){
		StringBuilder cmd = new StringBuilder();
		cmd.append(deployInfo.getPath());
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append(".");
		cmd.append(getCurrentTime());
		cmd.append(".tar.gz ");
		return cmd.toString();
	}

	private String getCurrentTime() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(new Date());
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = fillZero(calendar.get(Calendar.MONTH)+1);
		String day = fillZero(calendar.get(Calendar.DAY_OF_MONTH));
		String hour = fillZero(calendar.get(Calendar.HOUR_OF_DAY));
		String minute = fillZero(calendar.get(Calendar.MINUTE));
		StringBuilder time = new StringBuilder();
		time.append(year);
		time.append("_");
		time.append(month);
		time.append("_");
		time.append(day);
		time.append("_");
		time.append(hour);
		time.append("_");
		time.append(minute);
		return time.toString();
	}
	
	private static String fillZero(int time) {
		if(time < 10){
			return "0" + time;
		}
		return String.valueOf(time);
	}

	public Command getTarJetty(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("tar -zcvf ");
		cmd.append(getTarServiceFileName(deployInfo));
		cmd.append(deployInfo.getPath()+"/WEB-INF ");
		cmd.append(deployInfo.getPath()+"/META-INF ");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTarTomcat(DeployInfo deployInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getCreateCurrentFolder(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("mkdir ");
		cmd.append(deployInfo.getPath());
		cmd.append("/");
		cmd.append("bak_file_");
		cmd.append(getCurrentTime());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getCopyAppFile(DeployInfo deployInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getDeleteJetty(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("rm -rf ");
		cmd.append(deployInfo.getPath()+"/WEB-INF ");
		cmd.append(deployInfo.getPath()+"/META-INF ");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getDeleteService(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("rm -rf ");
		cmd.append(deployInfo.getPath()+"/conf ");
		cmd.append(deployInfo.getPath()+"/lib ");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getDeleteApp(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("rm -rf ");
		cmd.append(deployInfo.getPath());
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTarApp(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("tar -zcvf ");
		cmd.append(getTarServiceFileName(deployInfo));
		cmd.append(deployInfo.getPath());
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getReplaceServiceProperties(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf ");
		cmd.append(getMap().get("deploy.propertiesPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/conf/*.properties ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/conf");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getReplaceJettyProperties(DeployInfo deployInfo) {
		//WEB-INF classes *.properties *.xml *.conf
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf ");
		cmd.append(getMap().get("deploy.propertiesPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/webapps/ROOT/WEB-INF/classes/* ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/WEB-INF/classes");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTransferAppToTomcat(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append(" ");
		cmd.append(deployInfo.getPath());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTransferServiceToFile(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/* ");
		cmd.append(deployInfo.getPath());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getTransferProjectToJettyt(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf ");
		cmd.append(getMap().get("deploy.tempPath"));
		cmd.append("/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/* ");
		cmd.append(deployInfo.getPath());
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getRestartService(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append(deployInfo.getPath());
		cmd.append("/bin/start.sh");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getRestartJetty(DeployInfo deployInfo) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("/home/");
		cmd.append(deployInfo.getFileName());
		cmd.append("/jettystart.sh");
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}
	
	public Command getMkdirBeforeCopyFile(Property property) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("mkdir -p ");
		cmd.append(getMap().get("deploy.propertiesPath"));
		cmd.append("/");
		cmd.append(property.getPath());
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.SERVICE)){
			cmd.append("/conf");
		}
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.JETTY)){
			cmd.append("/webapps/ROOT/WEB-INF/classes");
		}
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getCopyFile(Property property) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("\\cp -rf  ");
		cmd.append(property.getFilePath());
		cmd.append("  ");
		cmd.append(getMap().get("deploy.propertiesPath"));
		cmd.append("/");
		cmd.append(property.getPath());
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.SERVICE)){
			cmd.append("/conf");
		}
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.JETTY)){
			cmd.append("/webapps/ROOT/WEB-INF/classes");
		}
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getSCopyFile(Property property) {
		//scp -r /home/cis-service/mi-web-smart.tar.gz root@172.30.0.10:/home/tempPath
		StringBuilder cmd = new StringBuilder();
		cmd.append("scp -r ");
		cmd.append(property.getFilePath());
		cmd.append(" ");
		cmd.append(getMap().get("nginx.userName"));
		cmd.append("@");
		cmd.append(getMap().get("nginx.host"));
		cmd.append(":");
		cmd.append(getMap().get("deploy.propertiesPath"));
		cmd.append("/");
		cmd.append(property.getPath());
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.SERVICE)){
			cmd.append("/conf");
		}
		if(DeployType.getDeployType(property.getDeployType()).equals(DeployType.JETTY)){
			cmd.append("/webapps/ROOT/WEB-INF/classes");
		}
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

	public Command getDeleteFiles(String path) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("rm -rf ");
		cmd.append(path);
		Command command = new Command();
		command.setCommand(cmd.toString());
		return command;
	}

}
