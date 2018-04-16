package com.seezoon.framework.common.utils;

/**
 * Bootstrap validate 远程校验返回规定数据格式
 * 
 * @author hdf 2018年4月15日
 */
public class BtRemoteValidateResult {

	private boolean valid;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private BtRemoteValidateResult(boolean valid) {
		super();
		this.valid = valid;
	}

	public static BtRemoteValidateResult valid(boolean valid) {
		return new BtRemoteValidateResult(valid);
	}
}
