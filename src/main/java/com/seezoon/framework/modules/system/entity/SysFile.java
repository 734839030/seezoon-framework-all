package com.seezoon.framework.modules.system.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.seezoon.framework.common.entity.BaseEntity;

public class SysFile extends BaseEntity<String>{

	/**
	 * 名称
	 */
	@NotNull
	@Length(min=1,max=200)
	private String name;

	/**
	 * 文件类型
	 */
	@NotNull
	@Length(min=1,max=100)
	private String contentType;

	/**
	 * 文件大小
	 */
	@NotNull
	private Long fileSize;

	/**
	 * 地址
	 */
	@NotNull
	@Length(min=1,max=200)
	private String relativePath;

	/**下面字段不和DB映射**/
	/**
	 * 文件完整访问地址
	 */
	private String fullUrl;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType == null ? null : contentType.trim();
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath == null ? null : relativePath.trim();
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

}