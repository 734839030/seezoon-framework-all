package com.seezoon.framework.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.modules.system.entity.SysUser;

public class ExcelHelperTest {

	@Test
	public void doParse07() throws Exception {
		ExcelUtils excelHelper = new ExcelUtils();
		List<SysUser> doParse03 = excelHelper.doParse07(new FileInputStream("/Users/hdf/Documents/temp/1.xlsx"),
				SysUser.class, new String[] { "loginName", "name", "mobile" });
		System.out.println(JSON.toJSONString(doParse03));
	}

	@Test
	public void doExport() throws Exception {
		ExcelUtils excelHelper = new ExcelUtils();
		List<SysUser> doParse07 = excelHelper.doParse07(new FileInputStream("/Users/hdf/Documents/temp/1.xlsx"),
				SysUser.class, new String[] { "loginName", "name", "mobile" });
		excelHelper.doExport("1.xlsx", new String[] { "loginName", "name", "mobile" }, SysUser.class, doParse07, new FileOutputStream("/Users/hdf/Documents/temp/2.xlsx"), 1);
	}
}
