package com.seezoon.framework.common.entity;

public class AdminUser {

	private String userId;
	private String deptId;

	
	public AdminUser(String userId, String deptId) {
		super();
		this.userId = userId;
		this.deptId = deptId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}
