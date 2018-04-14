package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysUserDao;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.entity.SysUserRole;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;

@Service
public class SysUserService extends CrudService<SysUserDao, SysUser> {

	public SysUser findByLoginName(String loginName) {
		Assert.hasLength(loginName, "loginName 为空");
		SysUser sysUser = new SysUser();
		sysUser.setLoginName(loginName);
		List<SysUser> list = this.findList(sysUser);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int save(SysUser t) {
		//处理密码
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		t.setSalt(salt);
		t.setPassword(ShiroUtils.sha256(t.getPassword(), t.getSalt()));
		int cnt = super.save(t);
		// 插入角色
		this.saveUserRole(t.getRoleIds(), t.getId());
		return cnt;
	}

	@Override
	public int updateSelective(SysUser t) {
		if (StringUtils.isNotEmpty(t.getPassword())) {
			SysUser sysUser = this.findById(t.getId());
			Assert.notNull(sysUser,"用户不存在");
			t.setPassword(ShiroUtils.sha256(t.getPassword(), sysUser.getSalt()));
		}
		int cnt = super.updateSelective(t);
		// 删除user关联的所有role
		this.d.deleteUserRoleByUserId(t.getId());
		this.saveUserRole(t.getRoleIds(), t.getId());
		return cnt;
	}

	private int saveUserRole(List<String> roleIds, String userId) {
		// 插入角色
		if (null != roleIds && !roleIds.isEmpty()) {
			List<SysUserRole> userRoleList = Lists.newArrayList();
			for (String roleId : roleIds) {
				userRoleList.add(new SysUserRole(userId, roleId));
			}
			return this.d.insertUserRole(userRoleList);
		}
		return 0;
	}
}
