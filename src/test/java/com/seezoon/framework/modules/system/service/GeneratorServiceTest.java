package com.seezoon.framework.modules.system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.modules.system.entity.SysGen;

public class GeneratorServiceTest extends BaseJunitTest {

	@Autowired
    SysGenService sysGenService;
	@Autowired
	GeneratorService generatorService;
	@Test
	public void codeGen() {
		SysGen sysGen = sysGenService.findById("d6b17698566e4c9ba45d62074af4fe2a");
		generatorService.codeGen(sysGen);
	}
}
