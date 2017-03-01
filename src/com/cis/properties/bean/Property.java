package com.cis.properties.bean;

import lombok.Data;

import com.cis.deploy.bean.DeployType;

@Data
public class Property {
	
	private String type;
	private String server;
	private String name;
	private String path;
	private String contents;
	private String deployType;
	private String filePath;
}
