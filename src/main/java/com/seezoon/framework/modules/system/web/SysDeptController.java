package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.TreeHelper;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysDept;
import com.seezoon.framework.modules.system.service.SysDeptService;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.shiro.User;

@RestController
@RequestMapping("${admin.path}/sys/dept")
public class SysDeptController extends BaseController {

	@Autowired
	private SysDeptService sysDeptService;
	private TreeHelper<SysDept> treeHelper = new TreeHelper<>();

	/**
	 * 用户管理那里的部门查询,只能看到自己及以下部门
	 * @param sysDept
	 * @return
	 */
	@PostMapping("/qryAllWithScope.do")
	public ResponeModel qryAllWithScope(SysDept sysDept) {
		User user = ShiroUtils.getUser();
		String deptId = user.getDeptId();
		if (ShiroUtils.isSuperAdmin(user.getUserId())) {
			return this.qryAll(sysDept);
		}
		//如果是非超级管理员返回自己部门及以下
		SysDept currentDept = sysDeptService.findById(deptId);
		Assert.notNull(currentDept, "当前所属部门为空");
		List<SysDept> list = sysDeptService.findByParentIds(currentDept.getParentIds() + deptId);
		list.add(currentDept);
		return ResponeModel.ok(list);
	}
	
	
	@PostMapping("/qryAll.do")
	public ResponeModel qryAll(SysDept sysDept) {
		sysDept.setSortField("sort");
		sysDept.setDirection(Constants.ASC);
		List<SysDept> list = sysDeptService.findList(sysDept);
		//数据机构调整
		return ResponeModel.ok(treeHelper.treeGridList(list));
	}
	@RequiresPermissions("sys:dept:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysDept sysDept = sysDeptService.findById(id);
		return ResponeModel.ok(sysDept);
	}
	@RequiresPermissions("sys:dept:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysDept sysDept, BindingResult bindingResult) {
		SysDept parent = null;
		if (StringUtils.isNotEmpty(sysDept.getParentId())) {
			parent = sysDeptService.findById(sysDept.getParentId());
		} 
		treeHelper.setParent(sysDept, parent);
		int cnt = sysDeptService.save(sysDept);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:dept:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysDept sysDept, BindingResult bindingResult) {
		SysDept parent = null;
		if (StringUtils.isNotEmpty(sysDept.getParentId())) {
			parent = sysDeptService.findById(sysDept.getParentId());
		} 
		treeHelper.setParent(sysDept, parent);
		int cnt = sysDeptService.updateSelective(sysDept);
		return ResponeModel.ok(cnt);
	}

	@RequiresPermissions("sys:dept:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysDeptService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
