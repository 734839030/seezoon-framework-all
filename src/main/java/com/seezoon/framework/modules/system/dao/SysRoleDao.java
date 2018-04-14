package com.seezoon.framework.modules.system.dao;

import java.util.List;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysRoleMenu;

public interface SysRoleDao extends CrudDao<SysRole>{
    public int deleteRoleMenuByRoleId(String roleId);
    public int insertRoleMenu(List<SysRoleMenu> roleMenus);
    public List<SysRole> findByUserId(String userId);
}