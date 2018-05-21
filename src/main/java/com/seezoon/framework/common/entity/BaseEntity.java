package com.seezoon.framework.common.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 主键默认支持String 和 Long  Integer
 * 
 * @author hdf
 * 2018年3月31日
 * @param <PK>
 */
public class BaseEntity<PK> extends QueryEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private PK id;
	/**
	 * 创建者(后台人员使用)
	 */
	private String createBy;

	/**
	 * 创建时间(后台人员使用)
	 */
	private Date createDate;

	/**
	 * 更新者(后台人员使用)
	 */
	private String updateBy;

	/**
	 * 更新时间(后台人员使用)
	 */
	private Date updateDate;

	/**
	 * 备注信息
	 */
	private String remarks;
	
	/**
	 * 是否需要备份，子类改变该值可以变更写到备份表，需要单独建立table_bak
	 */
	@JSONField(serialize=false)
	private boolean isNeedBak =false;
	
	public boolean isNeedBak() {
		return isNeedBak;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
