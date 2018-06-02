package com.seezoon.framework.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.seezoon.framework.common.utils.CurrentThreadContext;
import com.seezoon.framework.common.utils.SQLFilterUtils;
import com.seezoon.framework.modules.system.utils.DataPermissionBuilder;

public class QueryEntity implements Serializable{
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 当前表的别名，默认sql语句没有别名，当有别名时候，为了避免冲突需要子类指定
	 */
	@JSONField(serialize=false)
	private String tableAlias;
	/**
	 * dataScopeFilter
	 */
	@JSONField(serialize=false)
	private String dsf;

	public String getDsf() {
		// /a 路径的后端请求需要后端需要，前端不需要
		AdminUser user = CurrentThreadContext.getUser();
		if (user != null && StringUtils.isEmpty(dsf)) {
			dsf = DataPermissionBuilder.build(this.getTableAlias());
		}
		return dsf;
	}

	
	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
	
	public String getTableAlias(){
		return tableAlias;
	}
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
		return SQLFilterUtils.sqlFilter(sortField);
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getDirection() {
		return SQLFilterUtils.sqlFilter(direction);
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
