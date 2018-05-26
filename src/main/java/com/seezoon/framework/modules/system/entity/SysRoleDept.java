package com.seezoon.framework.modules.system.entity;

/**
 * 角色拥有部门权限
 * @author hdf
 * 2018年5月26日
 */
public class SysRoleDept {

	private String roleId;
	private String deptId;
	
	public SysRoleDept(String roleId, String deptId) {
		super();
		this.roleId = roleId;
		this.deptId = deptId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
