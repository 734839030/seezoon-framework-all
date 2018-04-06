package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.seezoon.framework.modules.system.entity.SysMenu;
import com.seezoon.framework.modules.system.service.SysMenuService;

@RestController
@RequestMapping("${admin.path}/sys/menu")
public class SysMenuController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;
	private TreeHelper<SysMenu> treeHelper = new TreeHelper<>();

	@PostMapping("/qryAll.do")
	public ResponeModel qryPage(SysMenu sysMenu) {
		sysMenu.setSortField("sort");
		sysMenu.setDirection(Constants.ASC);
		List<SysMenu> list = sysMenuService.findList(sysMenu);
		return ResponeModel.ok(treeHelper.treeGridList(list));
	}

	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysMenu sysMenu = sysMenuService.findById(id);
		return ResponeModel.ok(sysMenu);
	}

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

	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysMenuService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
