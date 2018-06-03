package com.seezoon.framework.modules.system.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Maps;
import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.http.HttpRequestUtils;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysLoginLogDao;
import com.seezoon.framework.modules.system.entity.SysLoginLog;
import com.seezoon.framework.modules.system.entity.SysUser;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 登录日志Service
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-5-31 21:34:17
 */
@Service
public class SysLoginLogService extends CrudService<SysLoginLogDao, SysLoginLog>{

	@Autowired
	private SysUserService sysUserService;
	
	public void loginLogByUserId(String status,String userId,String ip,String userAgentStr) {
		try {
			Assert.hasLength(userId,"用户id 为空");
			Assert.hasLength(userAgentStr,"userAgent 为空");
			UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);  
			Browser browser = userAgent.getBrowser();  
			OperatingSystem os = userAgent.getOperatingSystem();
			SysLoginLog loginLog = new SysLoginLog();
			loginLog.setStatus(status);
			loginLog.setBrowserName(browser.getName() +" "+ browser.getVersion(userAgentStr));
			loginLog.setDeviceName(os.getName() + " "+ os.getDeviceType());
			loginLog.setIp(ip);
			if (StringUtils.isNotEmpty(ip)) {
				String ipInfo = HttpRequestUtils.doGet("http://ip.taobao.com/service/getIpInfo.php", Maps.newHashMap("ip",ip));
				if (StringUtils.isNotEmpty(ipInfo)) {
					JSONObject parseObject = JSON.parseObject(ipInfo);
					if (parseObject.containsKey("data")) {
						JSONObject data = parseObject.getJSONObject("data");
						loginLog.setArea(data.getString("region") + data.getString("city"));
					}
				}
			}
			loginLog.setUserId(userId);
			loginLog.setUserAgent(userAgentStr);
			loginLog.setLoginTime(new Date());
			loginLog.setCreateBy(userId);
			this.save(loginLog);
		} catch (Exception e) {
			logger.error("userId login log fail ",e);
		}
	}
	public void loginLogByLoginName(String status,String loginName,String ip,String userAgentStr) {
		SysUser user = sysUserService.findByLoginName(loginName);
		if (null != user) {
			this.loginLogByUserId(status, user.getId(), ip, userAgentStr);
		}
	}
	public SysLoginLog findLastLoginInfo(String userId) {
		Assert.hasLength(userId,"用户id 为空");
		SysLoginLog loginLog = new SysLoginLog();
		loginLog.setUserId(userId);
		loginLog.setStatus(SysLoginLog.SUCCESS);
		loginLog.setSortField("l.login_time");
		loginLog.setDirection(Constants.DESC);
		PageInfo<SysLoginLog> findByPage = this.findByPage(loginLog, 1, 2);
		List<SysLoginLog> list = findByPage.getList();
		if (!list.isEmpty()) {
			return list.get(list.size()-1);
		}
		return null;
	}
	
}
