package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.common.utils.AreaParser;
import com.seezoon.framework.modules.system.entity.SysArea;

public class AreaServiceTest extends BaseJunitTest{

	@Autowired
	private SysAreaService sysAreaService;
	
	@Test
	public void genArea() throws Exception {
		List<SysArea> parse = AreaParser.parse();
		parse.forEach((v)->{
			sysAreaService.save(v);
			System.out.println("处理中");
		});
		System.out.println("处理完成");
	}
	
}
