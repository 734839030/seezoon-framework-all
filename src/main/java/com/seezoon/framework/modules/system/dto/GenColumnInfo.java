package com.seezoon.framework.modules.system.dto;

/**
 * 代码生成每列的信息
 * 
 * @author hdf 2018年4月27日
 */
public class GenColumnInfo implements Comparable<GenColumnInfo> {

	/**
	 * DB 列名称
	 */
	private String dbColumnName;
	/**
	 * 列备注
	 */
	private String columnComment;
	/**
	 * 主键类型 如PRI
	 */
	private String columnKey;
	/**
	 * 主键额外说明 如auto_increment
	 */
	private String extra;
	/**
	 * 列类型 eg:varchar(64)
	 */
	private String columnType;
	/**
	 * 数据类型eg:varchar
	 */
	private String dataType;
	/**
	 * 字段长度eg:64
	 */
	private Long maxLength;
	/**
	 * 对应的java类型
	 */
	private String javaType;
	/**
	 * 对应的mysbtis JDBC类型
	 */
	private String jdbcType;
	/**
	 * 字段名称
	 */
	private String javaFieldName;
	/**
	 * 是否可空1: 可;0:不可以
	 */
	private String nullable;
	/**
	 * 是否可插入1: 可;0:不可以
	 */
	private String insert;
	/**
	 * 是否可更新1: 可;0:不可以
	 */
	private String update;
	/**
	 * 是否列表展示1: 可;0:不可以
	 */
	private String list;
	/**
	 * 是否列表排序1: 可;0:不可以
	 */
	private String sortable;
	/**
	 * 是否列表展示1: 可;0:不可以
	 */
	private String search;
	/**
	 * 查询方式
	 */
	private String queryType;
	/**
	 * 表单类型
	 */
	private String inputType;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 排序
	 */
	private Integer sort;

	public String getDbColumnName() {
		return dbColumnName;
	}

	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getInsert() {
		return insert;
	}

	public void setInsert(String insert) {
		this.insert = insert;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	@Override
	public int compareTo(GenColumnInfo o) {
		return this.sort - o.getSort();
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

}
