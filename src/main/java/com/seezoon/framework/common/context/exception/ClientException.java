package com.seezoon.framework.common.context.exception;

/**
 * 自定义异常方便后续扩展
 * 
 * @author hdf 2018年4月20日
 */
public class ClientException extends RuntimeException {

	public ClientException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ClientException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ClientException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
