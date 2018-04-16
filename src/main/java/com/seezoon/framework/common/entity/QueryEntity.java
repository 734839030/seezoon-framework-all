package com.seezoon.framework.common.entity;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class QueryEntity {

	/**
	 * 排序字段，对应db字段名
	 */
	private String sortField;
	/**
	 * 升降序
	 */
	private String direction;

	/**
	 * 页码
	 */
	@JSONField(serialize=false)
	private Integer page = 1;
	/**
	 * 每页大小
	 */
	@JSONField(serialize=false)
	private Integer pageSize = 20;
	/**
	 * 自定义查询字段
	 */
	private Map<String, Object> ext;

	/**
	 * 添加自定义参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Map<String, Object> addProperty(String key, Object value) {
		if (null == value) {
			return ext;
		}
		if (ext == null) {
			ext = new HashMap<>(1);
		}
		ext.put(key, value);
		return ext;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Map<String, Object> getExt() {
		return ext;
	}

	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		if (null != pageSize && pageSize > 1000) {
			pageSize = 1000;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
