package com.cis.command.service;

import java.util.List;

import com.cis.deploy.bean.Command;
import com.cis.deploy.bean.DeployInfo;
import com.cis.properties.bean.Property;

public interface CommandService {
	
	public Command getMD5Command(String path);
	
	public Command getFileContent(String fileName);

	public Command getClearUploadDirCommand(String scanPath);

	public Command getTransferFile(DeployInfo deployInfo);
	
	public Command getTransferFile(String path);

	public Command getCopyFile(DeployInfo deployInfo);
	
	public Command getCopyFile(String  fileName);

	public Command getTarFile(DeployInfo deployInfo);
	
	public Command getTarFile(String fileName);

	public Command getStopService(DeployInfo deployInfo);

	public Command getPIDWithPort(String string);

	public Command getKillServiceWithPID(String pid);

	public Command getTarService(DeployInfo deployInfo);

	public Command getTarJetty(DeployInfo deployInfo);

	public Command getTarTomcat(DeployInfo deployInfo);

	public Command getCreateCurrentFolder(DeployInfo deployInfo);

	public Command getCopyAppFile(DeployInfo deployInfo);

	public Command getDeleteJetty(DeployInfo deployInfo);

	public Command getDeleteService(DeployInfo deployInfo);

	public Command getDeleteApp(DeployInfo deployInfo);

	public Command getTarApp(DeployInfo deployInfo);

	public Command getReplaceServiceProperties(DeployInfo deployInfo);

	public Command getReplaceJettyProperties(DeployInfo deployInfo);

	public Command getTransferAppToTomcat(DeployInfo deployInfo);

	public Command getTransferServiceToFile(DeployInfo deployInfo);

	public Command getTransferProjectToJettyt(DeployInfo deployInfo);

	public Command getRestartService(DeployInfo deployInfo);

	public Command getRestartJetty(DeployInfo deployInfo);

	public Command getCopyFile(Property property);

	public Command getSCopyFile(Property property);

	public Command getMkdirBeforeCopyFile(Property property);

	public Command getDeleteFiles(String path);

	public Command getMkdir(String path);

	public Command getBackupFile(String path);
	
	public Command getMkdirBackupFile(String path);

}
