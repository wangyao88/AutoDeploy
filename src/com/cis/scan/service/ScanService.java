package com.cis.scan.service;

import java.util.List;

public interface ScanService {
	
	public List<String> getMD5TXTContent(String fileName);
	
	public List<String> getMD5InDir(String path);

	public void clearUploadDir();
	
	public boolean isUploadSuccess();
	
	public boolean scan();

	public void deleteAllTempFlie();

	public void backupFile();

	public void createAllNeededDirectories();

}
