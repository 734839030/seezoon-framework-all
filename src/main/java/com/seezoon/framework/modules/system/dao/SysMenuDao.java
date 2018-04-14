package com.seezoon.framework.modules.system.dao;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysMenu;

public interface SysMenuDao extends CrudDao<SysMenu> {

	public List<SysMenu> findByRoleId(String roleId);
	public List<SysMenu> findByUserId(String userId);
	public int deleteRoleMenuByMenuId(Serializable menuId);

}