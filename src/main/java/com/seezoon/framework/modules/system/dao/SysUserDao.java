package com.seezoon.framework.modules.system.dao;

import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.entity.SysUserRole;

public interface SysUserDao extends CrudDao<SysUser> {

	public int deleteUserRoleByUserId(String userId);
	public int insertUserRole(List<SysUserRole> list);
}