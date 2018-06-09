package com.seezoon.framework.common.entity;

import java.io.Serializable;

public class TreeEntity<PK extends Serializable> extends BaseEntity<PK> {

	/**
	 * 父部门
	 */
	private String parentId;
	/**
	 * 父ids，按层级逗号分隔
	 */
	private String parentIds;
	/**
	 * 排序
	 */
	private Integer sort;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
