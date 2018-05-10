package com.seezoon.framework.modules.system.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Collections2;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.dto.GenColumnInfo;
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
	public ResponeModel save(@Validated @RequestBody SysGen sysGen, BindingResult bindingResult) {
		List<GenColumnInfo> columnInfos = sysGen.getColumnInfos();
		Collections.sort(columnInfos);
		sysGen.setColumns(JSON.toJSONString(columnInfos));
		int cnt = sysGenService.save(sysGen);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:gen:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated @RequestBody SysGen sysGen, BindingResult bindingResult) {
		List<GenColumnInfo> columnInfos = sysGen.getColumnInfos();
		Collections.sort(columnInfos);
		sysGen.setColumns(JSON.toJSONString(columnInfos));
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
	@RequiresPermissions("sys:gen:qry")
	@RequestMapping("/codeGen.do")
	public void codeGen(@RequestParam String id,HttpServletResponse response) throws IOException {
		byte[] codeGen = this.sysGenService.codeGen(id);
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename="+ CodecUtils.urlEncode("代码生成.zip")); 
		response.setContentLength(codeGen.length);
		ServletOutputStream output = response.getOutputStream();
		IOUtils.write(codeGen, output);
		IOUtils.closeQuietly(output);
	}
}
