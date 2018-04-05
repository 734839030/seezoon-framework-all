package com.seezoon.framework.modules.demo.web;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.demo.entity.DemoTest;
import com.seezoon.framework.modules.demo.service.DemoTestService;

@RestController
@RequestMapping("${admin.path}/demo/test")
public class DemoTestController extends BaseController {

	@Autowired
	private DemoTestService demoTestService;

	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(DemoTest demoTest) {
		PageInfo<DemoTest> page = demoTestService.findByPage(demoTest, demoTest.getPage(), demoTest.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		DemoTest demoTest = demoTestService.findById(id);
		return ResponeModel.ok(demoTest);
	}

	@PostMapping("/save.do")
	public ResponeModel save(@Validated DemoTest demoTest, BindingResult bindingResult) {
		int cnt = demoTestService.save(demoTest);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/update.do")
	public ResponeModel update(DemoTest demoTest) {
		int cnt = demoTestService.updateSelective(demoTest);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = demoTestService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
