package com.seezoon.framework.modules.system.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.context.test.BaseJunitTest;

public class SysFileDaoTest extends BaseJunitTest{

	@Autowired
	private SysFileDao sysFileDao;
	
	@Test
	public void testDeleteByPrimaryKey() {
		sysFileDao.deleteByPrimaryKey("1", null);
	}

}
