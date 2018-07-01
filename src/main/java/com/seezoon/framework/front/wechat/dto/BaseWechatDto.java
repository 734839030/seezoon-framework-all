package com.seezoon.framework.front.wechat.dto;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseWechatDto {

	private String errcode;
	private String errmsg;
	
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	@JsonIgnore
	public boolean isSuccess() {
		return StringUtils.isEmpty(errcode) || "0".equals(errcode);
	}
}
