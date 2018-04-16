package com.seezoon.framework.modules.system.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.system.entity.SysRole;

public class SysRoleServiceTest extends BaseJunitTest{

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;
	@Test
	public  void t1() {
		System.out.println(sysUserService.encryptPwd("123456", "rNBdNtjuefmwLGzXjHoN"));
	}
	@Test
	public void testSaveSysRole() {
		for (int i = 0;i<10000;i++) {
			SysRole sysRole = new SysRole();
			sysRole.setDataScope("1");
			sysRole.setName("role" + RandomStringUtils.randomAscii(5));
			sysRoleService.save(sysRole);
		}
	}

}
