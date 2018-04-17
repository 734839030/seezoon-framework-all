package com.seezoon.framework.modules.system.dao;

import java.io.Serializable;
import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysRoleMenu;

public interface SysRoleDao extends CrudDao<SysRole>{
    public int deleteRoleMenuByRoleId(Serializable roleId);
    public int deleteUserRoleByRoleId(Serializable roleId);
    public int insertRoleMenu(List<SysRoleMenu> roleMenus);
    public List<SysRole> findByUserId(String userId);
}