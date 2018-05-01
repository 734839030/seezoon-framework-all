package com.seezoon.framework.modules.system.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.system.dto.DbTable;
import com.seezoon.framework.modules.system.dto.DbTableColumn;

public class SysGenDaoTest extends BaseJunitTest {

	@Autowired
	SysGenDao sysGenDao;

	@Test
	public void test() {
		List<DbTable> list = sysGenDao.findTable(null);
		logger.info(JSON.toJSONString(list));
	}
	@Test
	public void t2() {
		List<DbTableColumn> list = sysGenDao.findColumnByTableName("sys_user");
		logger.info(JSON.toJSONString(list));
	}

}
