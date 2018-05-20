package com.seezoon.framework.modules.system.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.seezoon.framework.common.entity.BaseEntity;

public class SysUser extends BaseEntity<String> {

	/**
	 * 部门
	 */
	@NotNull
	@Length(min = 1, max = 32)
	private String deptId;

	/**
	 * 登录名
	 */
	@NotNull
	@Length(min = 1, max = 50)
	private String loginName;

	/**
	 * 密码
	 */
	@JSONField(serialize = false)
	private String password;

	/**
	 * 盐
	 */
	@JSONField(serialize = false)
	private String salt;

	/**
	 * 姓名
	 */
	@NotNull
	@Length(min = 1, max = 50)
	private String name;

	/**
	 * 手机
	 */
	@Length(max = 50)
	private String mobile;

	/**
	 * 用户头像
	 */
	@Length(max = 100)
	private String photo;

	/**
	 * 邮箱
	 */
	@Length(max = 50)
	private String email;

	/**
	 * 用户类型，业务扩展用，读取字典
	 */
	@Length(max = 2)
	private String userType;

	/**
	 * 状态1：正常，0：禁用
	 */
	@NotNull
	@Length(min = 1, max = 1)
	@Pattern(regexp = "1|0")
	private String status;
	/** DB 字段截止 **/
	private String deptName;
	/**
	 * 用户所拥有的角色ID
	 */
	private List<String> roleIds;
	private List<SysMenu> menus;
	public static final String STATUS_NORMAL = "1";
	public static final String STATUS_STOP = "0";
	/**
	 * 图像完整路径
	 */
	private String photoFullUrl;

	@Override
	public boolean isNeedBak() {
		return Boolean.TRUE;
	}

	@Override
	public String getTableAlias() {
		return "u";
	}
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo == null ? null : photo.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType == null ? null : userType.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<SysMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SysMenu> menus) {
		this.menus = menus;
	}

	public String getPhotoFullUrl() {
		return photoFullUrl;
	}

	public void setPhotoFullUrl(String photoFullUrl) {
		this.photoFullUrl = photoFullUrl;
	}

}