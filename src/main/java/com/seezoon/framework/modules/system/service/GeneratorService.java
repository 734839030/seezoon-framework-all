package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.beust.jcommander.internal.Lists;
import com.seezoon.framework.modules.system.dto.DbTable;
import com.seezoon.framework.modules.system.dto.DbTableColumn;
import com.seezoon.framework.modules.system.dto.GenColumnInfo;
import com.seezoon.framework.modules.system.entity.SysGen;
import com.seezoon.framework.modules.system.utils.GenTypeMapping;

@Service
public class GeneratorService {

	/**
	 * 编辑默认不勾选
	 */
	private static final String[] defaultNotUpdates = {"id","create_by","create_date"};
	private static final String[] defaultNotLists = {"id","create_by","create_date","update_by","remarks"};

	
	public SysGen getDefaultGenInfo(DbTable table, List<DbTableColumn> columns) {
		Assert.notEmpty(columns, "DB 字段不存在");
		Assert.notNull(table, "表不存在");
		SysGen sysGen = new SysGen();
		String tableName = table.getName();
		sysGen.setTableName(table.getName());
		sysGen.setMenuName(table.getComment());
		// 模块名默认下滑线分隔第一组
		String[] split = tableName.split("_");
		sysGen.setModuleName(split[0]);
		sysGen.setTemplate("1");
		sysGen.setClassName(camelToUnderline(tableName));
		List<GenColumnInfo> genColumnInfos = Lists.newArrayList();
		// 处理列
		for (DbTableColumn column : columns) {
			GenColumnInfo genColumnInfo = new GenColumnInfo();
			genColumnInfo.setDbColumnName(column.getName());
			genColumnInfo.setColumnComment(column.getComment());
			genColumnInfo.setColumnType(column.getColumnType());
			genColumnInfo.setDataType(column.getDataType());
			genColumnInfo.setMaxLength(column.getMaxlength());
			genColumnInfo.setJavaType(GenTypeMapping.getDbJavaMapping(column.getDataType()));
			genColumnInfo.setJavaFieldName(columnToJava(column.getName()));
			genColumnInfo.setNullable(column.getNullable());
			genColumnInfo.setInsert("1");
			if (!ArrayUtils.contains(defaultNotUpdates, column.getName())) {
				genColumnInfo.setUpdate("1");
			}
			if (!ArrayUtils.contains(defaultNotLists, column.getName())) {
				genColumnInfo.setList("1");
			}
			genColumnInfo.setSort(column.getSort());
			genColumnInfos.add(genColumnInfo);
		}
		sysGen.setColumnInfo(genColumnInfos);
		return sysGen;
	}

	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return StringUtils.uncapitalize(camelToUnderline(columnName));
	}

	/**
	 * 驼峰
	 */
	public static String camelToUnderline(String name) {
		return WordUtils.capitalizeFully(name, new char[] { '_' }).replace("_", "");
	}
}
