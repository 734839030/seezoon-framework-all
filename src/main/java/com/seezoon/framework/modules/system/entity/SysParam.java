package com.seezoon.framework.modules.system.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
	@NotNull
	@Length(min=1,max=50)
	private String name;

	/**
	 * 键
	 */
	@NotNull
	@Length(min=1,max=50)
	private String paramKey;

	/**
	 * 值
	 */
	@NotNull
	@Length(min=1,max=50)
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