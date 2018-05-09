package com.seezoon.framework.modules.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.seezoon.framework.common.dao.CrudDao;
import com.seezoon.framework.modules.system.dto.DbTable;
import com.seezoon.framework.modules.system.dto.DbTableColumn;
import com.seezoon.framework.modules.system.entity.SysGen;

public interface SysGenDao extends CrudDao<SysGen>{
    
	public List<DbTable> findTable(@Param("tableName") String tableName);
	public List<DbTableColumn> findColumnByTableName(@Param("tableName") String tableName);
	public String findPkType(@Param("tableName") String tableName);
}