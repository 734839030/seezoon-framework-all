package com.seezoon.framework.modules.system.service;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.common.utils.FreeMarkerUtils;
import com.seezoon.framework.modules.system.entity.SysGen;

public class GeneratorServiceTest extends BaseJunitTest {

	@Autowired
    SysGenService sysGenService;
	@Autowired
	GeneratorService generatorService;
	@Test
	public void codeGen() {
		SysGen sysGen = sysGenService.findById("4426a306855647fd9fc495037334be84");
		generatorService.codeGen(sysGen);
	}
	public static void main(String[] args) {
		System.out.println(FreeMarkerUtils.renderStringTemplate("11${id}11", new SysGen()));
	}
}
