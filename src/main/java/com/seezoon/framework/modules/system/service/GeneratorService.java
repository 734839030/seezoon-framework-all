package com.seezoon.framework.modules.system.service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.beust.jcommander.internal.Lists;
import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.utils.FreeMarkerUtils;
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
	private static final String[] defaultNotUpdates = { "id", "create_by", "create_date" };
	/**
	 * 列表默认不展示
	 */
	private static final String[] defaultNotLists = { "id", "create_by", "create_date", "update_by", "remarks" };
	/**
	 * 默认有的字段
	 */
	private static final String[] defaultFields = { "id", "create_by", "create_date", "update_by", "update_date",
			"remarks" };

	/**
	 * 待生成模板
	 */
	private static final String[] ftls = {"mapper.xml.ftl","entity.java.ftl","dao.java.ftl","service.java.ftl","controller.java.ftl"};
	/**
	 * 第一次从表结构获取的默认生成方案
	 * 
	 * @param table
	 * @param columns
	 * @return
	 */
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
		if (split.length > 1) {
			sysGen.setFunctionName(split[1]);
		}
		sysGen.setTemplate("1");
		sysGen.setClassName(camelToUnderline(tableName));
		List<GenColumnInfo> genColumnInfos = Lists.newArrayList();
		// 处理列
		for (DbTableColumn column : columns) {
			GenColumnInfo genColumnInfo = new GenColumnInfo();
			genColumnInfo.setDbColumnName(column.getName());
			genColumnInfo.setColumnComment(column.getComment());
			genColumnInfo.setColumnType(column.getColumnType());
			genColumnInfo.setColumnKey(column.getColumnKey());
			genColumnInfo.setExtra(column.getExtra());
			genColumnInfo.setDataType(column.getDataType());
			genColumnInfo.setMaxLength(column.getMaxlength());
			genColumnInfo.setJavaType(GenTypeMapping.getDbJavaMapping(column.getDataType()));
			genColumnInfo.setJdbcType(GenTypeMapping.getDbMybatisMapping(column.getDataType()));
			genColumnInfo.setJavaFieldName(columnToJava(column.getName()));
			genColumnInfo.setNullable(column.getNullable());
			genColumnInfo.setInsert(Constants.YES);
			if (!ArrayUtils.contains(defaultNotUpdates, column.getName())) {
				genColumnInfo.setUpdate(Constants.YES);
			}
			if (!ArrayUtils.contains(defaultNotLists, column.getName())) {
				genColumnInfo.setList(Constants.YES);
			}
			genColumnInfo.setSort(column.getSort());
			genColumnInfos.add(genColumnInfo);
		}
		sysGen.setColumnInfos(genColumnInfos);
		return sysGen;
	}

	public void codeGen(SysGen sysGen) throws IOException {
		Assert.notNull(sysGen, "生成方案为空");
		Assert.notNull(sysGen.getColumnInfos(), "生成方案为空");
		List<GenColumnInfo> columnInfos = sysGen.getColumnInfos();
		for (GenColumnInfo column : columnInfos) {
			String javaType = column.getJavaType();
			if (!ArrayUtils.contains(defaultFields, column.getDbColumnName())) {
				if ("BigDecimal".equals(javaType)) {
					sysGen.setHasBigDecimal(true);
				}
				if ("Date".equals(javaType)) {
					sysGen.setHasDate(true);
				}
				if (Constants.YES.equals(column.getSearch())) {
					sysGen.setHasSearch(true);
				}
			}
			//打字单
			if (column.getJdbcType().equals("LONGVARCHAR")) {
				sysGen.setHasBlob(true);
			}
		}
		ZipOutputStream zos = new ZipOutputStream(new ByteArrayOutputStream());
		String entity = FreeMarkerUtils.renderTemplate("gen/entity.java.ftl", sysGen);
		zos.putNextEntry(new ZipEntry("src/main/java/com/seezoon/framework/modules/system"));
		IOUtils.write(entity,zos);
		String renderTemplate1 = FreeMarkerUtils.renderTemplate("gen/mapper.xml.ftl", sysGen);
		System.out.println(renderTemplate1);
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
