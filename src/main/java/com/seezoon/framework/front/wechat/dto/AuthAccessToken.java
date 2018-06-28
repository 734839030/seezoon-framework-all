package com.seezoon.framework.front.wechat.dto;

/**
 * 通过code 换取access token
 * @author hdf
 * 2018年6月28日
 */
public class AuthAccessToken extends BaseWechatDto{

	private String access_token;
	private String openid;
	private String scope;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
