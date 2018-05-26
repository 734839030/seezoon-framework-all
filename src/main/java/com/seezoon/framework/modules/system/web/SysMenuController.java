package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.Constants;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.TreeHelper;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysMenu;
import com.seezoon.framework.modules.system.service.SysMenuService;

@RestController
@RequestMapping("${admin.path}/sys/menu")
public class SysMenuController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;
	private TreeHelper<SysMenu> treeHelper = new TreeHelper<>();

	@PostMapping("/qryAll.do")
	public ResponeModel qryAll(SysMenu sysMenu) {
		sysMenu.setSortField("sort");
		sysMenu.setDirection(Constants.ASC);
		List<SysMenu> list = sysMenuService.findList(sysMenu);
		return ResponeModel.ok(treeHelper.treeGridList(list));
	}
	@RequiresPermissions("sys:menu:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysMenu sysMenu = sysMenuService.findById(id);
		return ResponeModel.ok(sysMenu);
	}
	@RequiresPermissions("sys:menu:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysMenu sysMenu, BindingResult bindingResult) {
		SysMenu parent = null;
		if (StringUtils.isNotEmpty(sysMenu.getParentId())) {
			parent = sysMenuService.findById(sysMenu.getParentId());
		} 
		treeHelper.setParent(sysMenu, parent);
		int cnt = sysMenuService.save(sysMenu);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:menu:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysMenu sysMenu, BindingResult bindingResult) {
		SysMenu parent = null;
		if (StringUtils.isNotEmpty(sysMenu.getParentId())) {
			parent = sysMenuService.findById(sysMenu.getParentId());
		} 
		treeHelper.setParent(sysMenu, parent);
		int cnt = sysMenuService.updateSelective(sysMenu);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:menu:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysMenuService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:menu:save")
	@PostMapping("/batchSave.do")
	public ResponeModel batchSave(@RequestBody List<SysMenu> list ) {
		//直接用list接收到的json 参数实际上是jsonObject，强转到SysMenu 会报错,下列性能不好，因为不想循环List 转化
		sysMenuService.batchSave(JSON.parseArray(JSON.toJSONString(list), SysMenu.class));
		return ResponeModel.ok();
	}
	@PostMapping("/qryByRoleId.do")
	public ResponeModel qryMenuByRoleId(@RequestParam(required=false) String roleId) {
		return ResponeModel.ok(sysMenuService.findByRoleId(roleId));
	}
}
