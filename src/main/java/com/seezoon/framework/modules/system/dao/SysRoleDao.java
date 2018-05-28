package com.seezoon.framework.modules.system.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysRoleDept;
import com.seezoon.framework.modules.system.entity.SysRoleMenu;

public interface SysRoleDao extends CrudDao<SysRole>{
    public int deleteRoleMenuByRoleId(Serializable roleId);
    public int deleteUserRoleByRoleId(Serializable roleId);
    public int deleteUserRoleByRoleIdAndUserId(@Param("roleId") String roleId,@Param("userId") String userId);
    public int insertUserRole(@Param("roleId") String roleId,@Param("userId") String userId);
    public int insertRoleMenu(List<SysRoleMenu> roleMenus);
    public List<SysRole> findByUserId(String userId);
    public int deleteRoleDeptByRoleId(Serializable roleId);
    public int insertRoleDept(List<SysRoleDept> list);
    public List<String> selectDeptIdsByRoleId(String roleId);
}