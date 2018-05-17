package com.seezoon.framework.modules.demo.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.seezoon.framework.modules.demo.entity.DemoGen;
import com.seezoon.framework.modules.demo.service.DemoGenService;

/**
 * 生成案例controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-5-18 0:30:08
 */
@RestController
@RequestMapping("${admin.path}/demo/gen")
public class DemoGenController extends BaseController {

	@Autowired
	private DemoGenService demoGenService;

	@RequiresPermissions("demo:gen:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(DemoGen demoGen) {
		PageInfo<DemoGen> page = demoGenService.findByPage(demoGen, demoGen.getPage(), demoGen.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("demo:gen:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		DemoGen demoGen = demoGenService.findById(id);
		//富文本处理
        if (null != demoGen) {
		    if (StringUtils.isNotEmpty(demoGen.getRichText())) {
				demoGen.setRichText(StringEscapeUtils.unescapeHtml4(demoGen.getRichText()));
			}
		}
		return ResponeModel.ok(demoGen);
	}
	@RequiresPermissions("demo:gen:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated DemoGen demoGen, BindingResult bindingResult) {
		int cnt = demoGenService.save(demoGen);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("demo:gen:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated DemoGen demoGen, BindingResult bindingResult) {
		int cnt = demoGenService.updateSelective(demoGen);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("demo:gen:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = demoGenService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
