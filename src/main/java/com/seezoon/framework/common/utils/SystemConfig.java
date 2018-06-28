package com.seezoon.framework.common.utils;

import com.seezoon.framework.common.context.utils.PropertyUtil;

public class SystemConfig {

	private static String adminPath = PropertyUtil.getString("admin.path");
	private static String frontPath = PropertyUtil.getString("front.path");
	public static String getAdminPath() {
		return adminPath;
	}
	public static void setAdminPath(String adminPath) {
		SystemConfig.adminPath = adminPath;
	}
	public static String getFrontPath() {
		return frontPath;
	}
	public static void setFrontPath(String frontPath) {
		SystemConfig.frontPath = frontPath;
	}

	
}
