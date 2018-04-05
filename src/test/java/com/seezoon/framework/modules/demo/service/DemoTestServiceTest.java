package com.seezoon.framework.modules.demo.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.demo.entity.DemoTest;
import com.seezoon.framework.modules.system.web.SysParamController;

public class DemoTestServiceTest extends BaseJunitTest{
	
	@Autowired
	private DemoTestService demoTestService;
	@Autowired
	private SysParamController sysParamController;

	@Test
	public void testSave() {
		DemoTest demoTest = new DemoTest();
		demoTest.setAge(110);
		demoTest.setName("hello world");
		demoTestService.save(demoTest);
	}

	@Test
	public void testUpdateSelective() {
		logger.info(JSON.toJSONString(sysParamController.get("1")));
	}

	@Test
	public void testUpdateById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindList() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPageTIntIntBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPageTIntInt() {
		fail("Not yet implemented");
	}

}
