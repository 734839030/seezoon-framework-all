package com.seezoon.framework.front.wechat.dto;

/**
 * 小程序登录返回值
 * 
 * @author hdf 2018年7月11日
 */
public class JsCode2session extends BaseWechatDto {

	private String openid;
	/**
	 * 微信的session
	 */
	private String session_key;
	private String unionid;
	/**
	 * 系统sessionId
	 */
	private String seezoonSessionKey;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getSeezoonSessionKey() {
		return seezoonSessionKey;
	}

	public void setSeezoonSessionKey(String seezoonSessionKey) {
		this.seezoonSessionKey = seezoonSessionKey;
	}

}
