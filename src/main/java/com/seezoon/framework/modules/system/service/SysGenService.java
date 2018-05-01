package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysGenDao;
import com.seezoon.framework.modules.system.dto.DbTable;
import com.seezoon.framework.modules.system.dto.DbTableColumn;
import com.seezoon.framework.modules.system.entity.SysGen;

/**
 * 生成方案
 * 
 * @author hdf 2018年4月26日
 */
@Service
public class SysGenService extends CrudService<SysGenDao, SysGen> {

	@Autowired
	private GeneratorService generatorService;
	
	public List<DbTable> findTables() {
		return this.d.findTable(null);
	}

	public DbTable findTableByName(String tableName) {
		Assert.hasLength(tableName, "表名为空");
		List<DbTable> list = this.d.findTable(tableName);
		return list.isEmpty() ? null : list.get(0);
	}
	public List<DbTableColumn> findColumnByTableName(String tableName) {
		Assert.hasLength(tableName, "表名为空");
		return this.d.findColumnByTableName(tableName);
	}
	/**
	 * 根据表名获得默认的生成方案
	 * @param tableName
	 * @return
	 */
	public SysGen getDefaultGenInfoByTableName(String tableName) {
		DbTable table = this.findTableByName(tableName);
		Assert.notNull(table,tableName + " 不存在");
		List<DbTableColumn> columns = this.findColumnByTableName(tableName);
		SysGen sysGen = generatorService.getDefaultGenInfo(table, columns);
		return sysGen;
	}
	
	
}
