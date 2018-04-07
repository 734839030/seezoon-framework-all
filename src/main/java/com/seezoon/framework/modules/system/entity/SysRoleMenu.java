package com.seezoon.framework.modules.system.entity;

public class SysRoleMenu {

	private String roleId;
	private String menuId;

	public SysRoleMenu(String roleId, String menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
