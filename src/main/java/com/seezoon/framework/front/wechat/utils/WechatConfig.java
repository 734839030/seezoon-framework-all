package com.seezoon.framework.front.wechat.utils;

import com.seezoon.framework.common.context.utils.PropertyUtil;

public class WechatConfig {

	private static String appID = PropertyUtil.getString("wechat.appID");
	private static String appsecret = PropertyUtil.getString("wechat.appsecret");
	private static String mchId = PropertyUtil.getString("wechat.mchId");
	private static String callBackBasePath = PropertyUtil.getString("wechat.callBackBasePath");
	private static String notifyUrl = callBackBasePath + "/public/wechat/payResult.do";
	//微信支付api 秘钥
	private static String mchKey = PropertyUtil.getString("wechat.mchKey");
	//下单机器ip
	private static String spbillCreateIp = PropertyUtil.getString("wechat.spbill_create_ip");
	
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
	public static String getMchId() {
		return mchId;
	}
	public static void setMchId(String mchId) {
		WechatConfig.mchId = mchId;
	}
	public static String getNotifyUrl() {
		return notifyUrl;
	}
	public static void setNotifyUrl(String notifyUrl) {
		WechatConfig.notifyUrl = notifyUrl;
	}
	public static String getMchKey() {
		return mchKey;
	}
	public static void setMchKey(String mchKey) {
		WechatConfig.mchKey = mchKey;
	}
	public static String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public static void setSpbillCreateIp(String spbillCreateIp) {
		WechatConfig.spbillCreateIp = spbillCreateIp;
	}
	public static String getCallBackBasePath() {
		return callBackBasePath;
	}
	public static void setCallBackBasePath(String callBackBasePath) {
		WechatConfig.callBackBasePath = callBackBasePath;
	}
	
}
