package com.cis.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Hashtable;

import lombok.extern.java.Log;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import expect4j.Expect4j;

@Log
public class MyShell {

	private Session session;
	private ChannelShell channel;
	private static Expect4j expect = null;
	private String ip;
	private int port;
	private String user;
	private String password;

	public MyShell(String ip, int port, String user, String password) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.password = password;
		expect = getExpect();
	}

	// 获得Expect4j对象，该对用可以往SSH发送命令请求
	private Expect4j getExpect() {
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, ip, port);
			session.setPassword(password);
			Hashtable<String, String> config = new Hashtable<String, String>();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			localUserInfo ui = new localUserInfo();
			session.setUserInfo(ui);
			session.connect();
			channel = (ChannelShell) session.openChannel("shell");
			Expect4j expect = new Expect4j(channel.getInputStream(),
					channel.getOutputStream());
			channel.connect();
			return expect;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 执行配置命令
	 * 
	 * @param commands
	 *            要执行的命令，为字符数组
	 * @return 执行是否成功
	 */
	public boolean executeCommands(String[] commands) {
		// 如果expect返回为0，说明登入没有成功
		if (expect == null) {
			return false;
		}
		try {
			boolean isSuccess = true;
			for (String strCmd : commands) {
				expect.send(strCmd);
				expect.send("\r");
			}
			return isSuccess;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}finally{
			expect.close();
			session.disconnect();
		}
		
	}

	// 登入SSH时的控制信息
	// 设置不提示输入密码、不显示登入信息等
	public static class localUserInfo implements UserInfo {
		String passwd;

		public String getPassword() {
			return passwd;
		}

		public boolean promptYesNo(String str) {
			return true;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			return true;
		}

		public void showMessage(String message) {

		}
	}

	public static void main(String[] args) {
//		String ip = "192.168.25.130";
//		int port = 22;
//		String user = "root";
//		String password = "wangyao";
//		MyShell shell = new MyShell(ip, port, user, password);
//		String[] commands = { "/home/osp/jettystart.sh" };
//		shell.executeCommands(commands);
		
		
		Session session = null;
		ChannelShell channel = null;
		StringBuilder sb = new StringBuilder();
		try {
			JSch jsch = new JSch();
			session = jsch.getSession("root", "192.168.25.130", 22);
			session.setPassword("wangyao");
			Hashtable<String, String> config = new Hashtable<String, String>();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			localUserInfo ui = new localUserInfo();
			session.setUserInfo(ui);
			session.connect();
			channel = (ChannelShell)session.openChannel("shell");
		    InputStream input = channel.getInputStream();
			OutputStream ops = channel.getOutputStream();
			PrintStream ps = new PrintStream(ops, true);
		    channel.connect(3000);
		    ps.println("/home/osp/jettystart.sh");
		    int size = 1024;
			final byte[] tmp = new byte[size];
		    while (true) {
				while (input.available() > 0) {
					int i = input.read(tmp, 0, 1024);
					System.out.println(i);
					if (i < 0) {
						break;
					}
					sb.append(new String(tmp, 0, i));
				}
				final String output = sb.toString();
				if (output.contains("done")) {
					break;
				}
				if (channel.isClosed()) {
					if (input.available() > 0) {
						int i = input.read(tmp, 0, 1024);
						sb.append(new String(tmp, 0, i));
					}
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			channel.disconnect();
			session.disconnect();
		}
		
	}

}
