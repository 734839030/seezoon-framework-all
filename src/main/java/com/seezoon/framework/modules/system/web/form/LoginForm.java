package com.seezoon.framework.modules.system.web.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class LoginForm {

	/**
	 * 登录名
	 */
	@NotNull
	@Length(min = 1, max = 50)
	private String loginName;
	/**
	 * 密码
	 */
	@NotNull
	@Length(min = 6)
	private String password;
	/**
	 * 记住功能
	 */
	private String rememberMe;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

}
