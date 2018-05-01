package com.seezoon.framework.modules.system.web;

import java.io.Serializable;

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
import com.seezoon.framework.modules.system.entity.SysGen;
import com.seezoon.framework.modules.system.service.SysGenService;

@RestController
@RequestMapping("${admin.path}/sys/gen")
public class SysGenController extends BaseController {

	@Autowired
	private SysGenService sysGenService;

	@RequiresPermissions("sys:gen:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysGen sysGen) {
		PageInfo<SysGen> page = sysGenService.findByPage(sysGen, sysGen.getPage(), sysGen.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequiresPermissions("sys:gen:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysGen sysGen = sysGenService.findById(id);
		return ResponeModel.ok(sysGen);
	}

	@RequiresPermissions("sys:gen:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysGen sysGen, BindingResult bindingResult) {
		int cnt = sysGenService.save(sysGen);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:gen:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysGen sysGen, BindingResult bindingResult) {
		int cnt = sysGenService.updateSelective(sysGen);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:gen:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysGenService.deleteById(id);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:gen:qry")
	@PostMapping("/qryTables.do")
	public ResponeModel qryTables() {
		return ResponeModel.ok(sysGenService.findTables());
	}
	@RequiresPermissions("sys:gen:qry")
	@PostMapping("/qryTableInfo.do")
	public ResponeModel qryTableInfo(@RequestParam String tableName) {
		return ResponeModel.ok(sysGenService.getDefaultGenInfoByTableName(tableName));
	}
}
