package com.seezoon.framework.modules.system.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.seezoon.framework.common.entity.BaseEntity;
import com.seezoon.framework.modules.system.dto.GenColumnInfo;

/**
 * 代码生成类
 * 
 * @author hdf 2018年4月26日
 */
public class SysGen extends BaseEntity<String> {

	/**
	 * 表名
	 */
	@NotNull
	@Length(min = 1, max = 32)
	private String tableName;
	/**
	 * 主键类型
	 */
	@NotNull
	@Length(min = 1, max = 10)
	private String pkType;
	/**
	 * 菜单名
	 */
	@NotNull
	@Length(min = 1, max = 50)
	private String menuName;

	/**
	 * 模块名
	 */
	@NotNull
	@Length(min = 1, max = 10)
	private String moduleName;
	/**
	 * 功能模块
	 */
	@NotNull
	@Length(min = 1, max = 20)
	private String functionName;
	/**
	 * 生成模板
	 */
	@NotNull
	@Length(min = 1, max = 1)
	private String template;

	/**
	 * 类名
	 */
	@NotNull
	@Length(min = 1, max = 50)
	private String className;

	/**
	 * 字段信息JSON
	 */
	@JSONField(serialize = false)
	private String columns;

	/** 不和db 对应 **/

	/**
	 * 生成列信息
	 */
	@NotNull
	private List<GenColumnInfo> columnInfos;
	/**
	 * 是否引入Date
	 */
	private boolean hasDate;
	/**
	 * 是否引入BigDecimal
	 */
	private boolean hasBigDecimal;
	/**
	 * 是否有大字段
	 */
	private boolean hasBlob;
	/**
	 * 是否有搜索
	 */
	private boolean hasSearch;

	/**
	 * 是否富文本
	 */
	private boolean hasRichText;
	
	/**
	 * 是否有文件上传
	 */
	private boolean hasFileUpload;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName == null ? null : tableName.trim();
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName == null ? null : menuName.trim();
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName == null ? null : moduleName.trim();
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template == null ? null : template.trim();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className == null ? null : className.trim();
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns == null ? null : columns.trim();
	}

	public List<GenColumnInfo> getColumnInfos() {
		return columnInfos;
	}

	public void setColumnInfos(List<GenColumnInfo> columnInfos) {
		this.columnInfos = columnInfos;
	}

	public String getPkType() {
		return pkType;
	}

	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	public boolean isHasDate() {
		return hasDate;
	}

	public void setHasDate(boolean hasDate) {
		this.hasDate = hasDate;
	}

	public boolean isHasBigDecimal() {
		return hasBigDecimal;
	}

	public void setHasBigDecimal(boolean hasBigDecimal) {
		this.hasBigDecimal = hasBigDecimal;
	}

	public boolean isHasSearch() {
		return hasSearch;
	}

	public void setHasSearch(boolean hasSearch) {
		this.hasSearch = hasSearch;
	}

	public boolean isHasBlob() {
		return hasBlob;
	}

	public void setHasBlob(boolean hasBlob) {
		this.hasBlob = hasBlob;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public boolean isHasRichText() {
		return hasRichText;
	}

	public void setHasRichText(boolean hasRichText) {
		this.hasRichText = hasRichText;
	}

	public boolean isHasFileUpload() {
		return hasFileUpload;
	}

	public void setHasFileUpload(boolean hasFileUpload) {
		this.hasFileUpload = hasFileUpload;
	}

}