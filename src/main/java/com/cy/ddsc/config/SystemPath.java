package com.cy.ddsc.config;

import java.util.ResourceBundle;

public class SystemPath {
	public static String FILE_PATH;
	public static String IP;
	public static String PORT;
	static{
		ResourceBundle rb = ResourceBundle.getBundle("system");
		FILE_PATH = rb.getString("file_path");
		IP = rb.getString("ip");
		PORT = rb.getString("port");
	}
}
