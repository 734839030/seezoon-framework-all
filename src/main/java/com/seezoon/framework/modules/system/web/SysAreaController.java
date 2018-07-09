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
import com.seezoon.framework.modules.system.entity.SysArea;
import com.seezoon.framework.modules.system.service.SysAreaService;

/**
 * 区域表controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-7-9 21:56:46
 */
@RestController
@RequestMapping("${admin.path}/sys/area")
public class SysAreaController extends BaseController {

	@Autowired
	private SysAreaService sysAreaService;

	@RequiresPermissions("sys:area:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysArea sysArea) {
		PageInfo<SysArea> page = sysAreaService.findByPage(sysArea, sysArea.getPage(), sysArea.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("sys:area:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysArea sysArea = sysAreaService.findById(id);
		//富文本处理
		return ResponeModel.ok(sysArea);
	}
}
