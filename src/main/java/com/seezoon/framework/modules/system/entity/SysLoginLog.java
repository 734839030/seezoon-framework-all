package com.seezoon.framework.modules.system.entity;

import com.seezoon.framework.common.entity.BaseEntity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

/**
 * 登录日志 Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * 
 * @author hdf 2018-5-31 21:34:17
 */
public class SysLoginLog extends BaseEntity<String> {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@NotNull
	@Length(min = 1, max = 32)
	private String userId;
	/**
	 * 登录状态0:成功;1.密码错误；2.已禁用;3.系统错误
	 */
	@NotNull
	@Length(min = 1, max = 1)
	private String status;
	/**
	 * 登录时间
	 */
	@NotNull
	private Date loginTime;
	/**
	 * IP地址
	 */
	@Length(max = 16)
	private String ip;
	/**
	 * 登录地区
	 */
	@Length(max = 20)
	private String area;
	/**
	 * 用户代理
	 */
	@NotNull
	@Length(min = 1, max = 1000)
	private String userAgent;
	/**
	 * 设备名称
	 */
	@Length(max = 100)
	private String deviceName;
	/**
	 * 浏览器名称
	 */
	@Length(max = 100)
	private String browserName;
	/********* 割 *******/

	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 用户名
	 */
	private String userName;

	public static final String  SUCCESS = "0";
	public static final String  PASSWORD_WRONG = "1";
	public static final String  USER_STAUTS_STOP = "2";
	public static final String  SYSTEM_ERROR = "3";


	@Override
	public String getTableAlias() {
		return "l";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}