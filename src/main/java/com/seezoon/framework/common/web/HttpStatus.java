package com.seezoon.framework.common.web;

/**
 * 自定义HTTP 状态码
 * 
 * @author hdf 2018年4月14日
 */
public enum HttpStatus {
	/**
	 * 需要登录
	 */
	NEED_LOGIN(310, "need login"),

	/**
	 * 需要权限
	 */
	NEED_PERMISSION(311, "need permission");

	private final int value;

	private final String reasonPhrase;

	HttpStatus(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public int getValue() {
		return value;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}
}
