package com.seezoon.framework.modules.system.dao;

import java.io.Serializable;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.entity.SysDept;

public interface SysDeptDao extends CrudDao<SysDept> {
	public int deleteRoleDeptByDeptId(Serializable deptId);
}