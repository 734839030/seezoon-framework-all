package com.seezoon.framework.front.wechat.utils;

import com.seezoon.framework.common.context.utils.PropertyUtil;

public class WechatConfig {

	private static String appID = PropertyUtil.getString("wechat.appID");
	private static String appsecret = PropertyUtil.getString("wechat.appsecret");
	public static String getAppID() {
		return appID;
	}
	public static void setAppID(String appID) {
		WechatConfig.appID = appID;
	}
	public static String getAppsecret() {
		return appsecret;
	}
	public static void setAppsecret(String appsecret) {
		WechatConfig.appsecret = appsecret;
	}
	
	
}
