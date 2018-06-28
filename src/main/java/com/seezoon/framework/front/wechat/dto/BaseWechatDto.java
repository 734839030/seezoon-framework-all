package com.seezoon.framework.front.wechat.dto;

import org.apache.commons.lang3.StringUtils;

import com.seezoon.framework.common.context.exception.ServiceException;

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
	
	public boolean isSuccess() {
		if (StringUtils.isEmpty(errcode) || "0".equals(errcode)) {
			return true;
		} else {
			return false;
		}
	}
}
