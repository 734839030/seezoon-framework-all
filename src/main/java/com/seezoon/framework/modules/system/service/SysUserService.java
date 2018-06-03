package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
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
		String salt = RandomStringUtils.randomAlphanumeric(20);
		t.setSalt(salt);
		//处理密码
		t.setPassword(encryptPwd(t.getPassword(), salt));
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
			t.setPassword(encryptPwd(t.getPassword(), sysUser.getSalt()));
		}
		int cnt = super.updateSelective(t);
		return cnt;
	}
	public int updateUserRoleSelective(SysUser t) {
		if (StringUtils.isNotEmpty(t.getPassword())) {
			SysUser sysUser = this.findById(t.getId());
			Assert.notNull(sysUser,"用户不存在");
			t.setPassword(encryptPwd(t.getPassword(), sysUser.getSalt()));
		}
		int cnt = super.updateSelective(t);
		// 删除user关联的所有role
		this.d.deleteUserRoleByUserId(t.getId());
		this.saveUserRole(t.getRoleIds(), t.getId());
		return cnt;
	}
	@Override
	public int deleteById(Serializable id) {
		//删除用户角色
		// 删除user关联的所有role
		this.d.deleteUserRoleByUserId(String.valueOf(id));
		return super.deleteById(id);
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
	public String encryptPwd(String password,String salt) {
		Assert.hasLength(password,"密码为空");
		Assert.hasLength(salt,"盐为空");
		return ShiroUtils.sha256(password, salt);
	}
	public boolean validatePwd(String password,String userId) {
		Assert.hasLength(password,"密码为空");
		Assert.hasLength(userId,"用户id为空");
		SysUser user = this.findById(userId);
		Assert.notNull(user,"用户不存在");
		return user.getPassword().equals(ShiroUtils.sha256(password, user.getSalt()));
	}
}
