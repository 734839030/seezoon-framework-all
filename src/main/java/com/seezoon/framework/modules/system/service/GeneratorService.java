package com.seezoon.framework.modules.system.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.google.common.collect.Lists;
import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.context.exception.ServiceException;
import com.seezoon.framework.common.service.BaseService;
import com.seezoon.framework.common.utils.FreeMarkerUtils;
import com.seezoon.framework.modules.system.dto.DbTable;
import com.seezoon.framework.modules.system.dto.DbTableColumn;
import com.seezoon.framework.modules.system.dto.GenColumnInfo;
import com.seezoon.framework.modules.system.entity.SysGen;
import com.seezoon.framework.modules.system.utils.GenEnum;
import com.seezoon.framework.modules.system.utils.GenTypeMapping;

@Service
public class GeneratorService extends BaseService{

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
	private static final String[] ftls = {"gen/mapper.xml.ftl","gen/entity.java.ftl","gen/dao.java.ftl","gen/service.java.ftl","gen/controller.java.ftl","gen/page.html.ftl","gen/javascript.js.ftl","gen/menu.sql.ftl"};
	
	private static final String javaFolder = "/seezoon-code/src/main/java/com/seezoon/framework/modules/";
	private static final String resourcesFolder = "/seezoon-code/src/main/resources/";
	private static final String staticFolder = "/seezoon-code/src/main/webapp/static/src/";


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
			//自增默认不插入
			if (!"auto_increment".equals(column.getExtra())) {
				genColumnInfo.setInsert(Constants.YES);
			}
			if (!ArrayUtils.contains(defaultNotUpdates, column.getName())) {
				genColumnInfo.setUpdate(Constants.YES);
			}
			if (!ArrayUtils.contains(defaultNotLists, column.getName())) {
				genColumnInfo.setList(Constants.YES);
			}
			if ("id".equals(column.getName())) {//默认是隐藏
				genColumnInfo.setInputType(GenEnum.InputType.HIDDEN.value());
			}
			if ("remarks".equals(column.getName())) {//默认文本域
				genColumnInfo.setInputType(GenEnum.InputType.TEXTAREA.value());
			}
			if ("Date".equals(genColumnInfo.getJavaType())) {//时间框
				genColumnInfo.setInputType(GenEnum.InputType.DATE.value());
			}
			if ("LONGVARCHAR".equals(genColumnInfo.getJdbcType())) {//大文本
				genColumnInfo.setInputType(GenEnum.InputType.TEXTAREA.value());
				genColumnInfo.setList(Constants.NO);
			}
			if ("Integer".equals(genColumnInfo.getJavaType()) || "Long".equals(genColumnInfo.getJavaType())) {//整数框
				genColumnInfo.setInputType(GenEnum.InputType.ZHENGSHU.value());
			}
			if ("Float".equals(genColumnInfo.getJavaType()) || "Double".equals(genColumnInfo.getJavaType()) || "BigDecimal".equals(genColumnInfo.getJavaType()) ) {//小数框
				genColumnInfo.setInputType(GenEnum.InputType.XIAOSHU.value());
			}
			genColumnInfo.setSort(column.getSort());
			genColumnInfos.add(genColumnInfo);
		}
		sysGen.setColumnInfos(genColumnInfos);
		return sysGen;
	}

	public byte[] codeGen(SysGen sysGen) {
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
			
			//大字段
			if ("LONGVARCHAR".equals(column.getJdbcType())) {
				sysGen.setHasBlob(true);
			}
			//富文本
			if ("richtext".equals(column.getInputType())) {
				sysGen.setHasRichText(true);
			}
			//文件上传
			if ("picture".equals(column.getInputType()) || "file".equals(column.getInputType())) {
				sysGen.setHasFileUpload(true);
			}
		}
		try {
			return this.zipCode(sysGen);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	private byte[] zipCode(SysGen sysGen) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);
		for (String ftl : ftls) {
			String content = FreeMarkerUtils.renderTemplate(ftl, sysGen);
			logger.info("ftl {}:\r\n{}",ftl,content);
			zos.putNextEntry(new ZipEntry(this.getZipEntryName(ftl,sysGen)));
			IOUtils.write(content, zos);
			zos.closeEntry();
		}
		//这个地方有点反人类，按道理应该在取到byte[] 后关闭，测试，zos.flush 也无效，顾提前关闭，将流都刷入到数组中。
		zos.close();
		byte[] byteArray = bos.toByteArray();
		return byteArray;
	}
	private String getZipEntryName(String ftl,SysGen sysGen) {
		String name = null;
		String moduleName = sysGen.getModuleName();
		String className = sysGen.getClassName();
		String functionName = sysGen.getFunctionName();

		if (ftl.contains("entity.java")) {
			name = javaFolder + moduleName + "/entity/" + className + ".java";
		} else if (ftl.contains("dao.java")) {
			name = javaFolder + moduleName + "/dao/" + className + "Dao.java";
		} else if (ftl.contains("service.java")) {
			name = javaFolder + moduleName + "/service/" + className + "Service.java";
		} else if (ftl.contains("controller.java")) {
			name = javaFolder + moduleName + "/web/" + className + "Controller.java";
		} else if (ftl.contains("mapper.xml")) {
			name = resourcesFolder + "datasource/mappings/" + moduleName + "/" + className + "Mapper.xml";
		} else if (ftl.contains("page.html")) {
			name = staticFolder + "pages/" + moduleName + "/" + functionName + ".html";
		} else if (ftl.contains("javascript.js")) {
			name = staticFolder + "js/" + moduleName + "/" + functionName + ".js";
		} else if (ftl.contains("menu.sql")) {
			name = resourcesFolder + "db/" + "sys_menu.sql";
		}
		Assert.hasLength(name, "zipEntryName 为空");
		return name;
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
