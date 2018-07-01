package com.seezoon.framework.front.wechat.dto;

/**
 * 根据token 获取js ticket
 * @author hdf
 * 2018年6月29日
 */
public class JsApiTicket extends BaseWechatDto{

	private String ticket;
	private String expires_in;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
}
