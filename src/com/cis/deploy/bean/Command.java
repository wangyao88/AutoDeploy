package com.cis.deploy.bean;

import java.util.List;

import lombok.Data;

@Data
public class Command {
	
	private String command;
	private List<String> result;
	private List<String> commands;
	private String subCommand;

}
