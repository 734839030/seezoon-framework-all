package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysUserDao;
import com.seezoon.framework.modules.system.entity.SysUser;

@Service
public class SysUserService extends CrudService<SysUserDao, SysUser> {

	public SysUser findByLoginName(String loginName) {
		Assert.hasLength(loginName,"loginName 为空");
		SysUser sysUser = new SysUser();
		sysUser.setLoginName(loginName);
		List<SysUser> list = this.findList(sysUser);
		return list.isEmpty()?null:list.get(0);
	}
}
