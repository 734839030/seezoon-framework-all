package com.seezoon.framework.modules.system.entity;

import com.seezoon.framework.common.entity.BaseEntity;

/**
 * 系统参数
 * 
 * @author hdf 2018年4月1日
 */
public class SysParam extends BaseEntity<String> {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 键
	 */
	private String paramKey;

	/**
	 * 值
	 */
	private String paramValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}