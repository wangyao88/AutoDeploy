package com.cis.deploy.bean;

public enum DeployType {
	
	JETTY,SERVICE,TOMCAT;

	public static DeployType getDeployType(String value) {
		if(value.contains("ROOT")){
			return JETTY;
		}
		if(value.contains("deploy_service")){
			return SERVICE;
		}
		if(value.contains("tomcat")){
			return TOMCAT;
		}
		if(value.equals("jetty")){
			return JETTY;
		}
		if(value.equals("service")){
			return SERVICE;
		}
		return null;
	}

}
