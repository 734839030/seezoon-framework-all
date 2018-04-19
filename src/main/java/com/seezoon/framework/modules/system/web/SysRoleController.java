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
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.service.SysRoleService;

@RestController
@RequestMapping("${admin.path}/sys/role")
public class SysRoleController extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;

	@RequiresPermissions("sys:role:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysRole sysRole) {
		PageInfo<SysRole> page = sysRoleService.findByPage(sysRole, sysRole.getPage(), sysRole.getPageSize());
		return ResponeModel.ok(page);
	}
	/**
	 * 添加修改用户时候会调用
	 * @return
	 */
	@PostMapping("/qryAll.do")
	public ResponeModel qryAll() {
		return ResponeModel.ok(this.sysRoleService.findList(null));
	}
	
	@RequiresPermissions("sys:role:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysRole sysRole = sysRoleService.findById(id);
		return ResponeModel.ok(sysRole);
	}

	@RequiresPermissions("sys:role:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysRole sysRole, BindingResult bindingResult) {
		int cnt = sysRoleService.save(sysRole);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:role:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysRole sysRole, BindingResult bindingResult) {
		int cnt = sysRoleService.updateSelective(sysRole);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:role:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysRoleService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
