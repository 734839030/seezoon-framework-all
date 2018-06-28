package com.seezoon.framework.front.wechat.dto;

/**
 * appid appsecret 获取的 2个小时有效期 需要缓存
 * @author hdf
 * 2018年6月28日
 */
public class Token extends BaseWechatDto{

	private String access_token;
	
	private int expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
}
