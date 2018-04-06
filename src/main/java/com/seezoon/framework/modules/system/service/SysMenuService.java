package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysMenuDao;
import com.seezoon.framework.modules.system.entity.SysMenu;

@Service
public class SysMenuService extends CrudService<SysMenuDao, SysMenu> {

	public List<SysMenu> findByParentIds(String parentIds){
		Assert.hasLength(parentIds,"parentIds 不能为空");
		SysMenu sysMenu = new SysMenu();
		sysMenu.setParentIds(parentIds);
		return this.findList(sysMenu);
	}
	@Override
	public int deleteById(Serializable id) {
		//删除下级部门
		SysMenu sysMenu = this.findById(id);
		Assert.notNull(sysMenu,"菜单不能为空");
		List<SysMenu> list = this.findByParentIds(sysMenu.getParentIds() + sysMenu.getId());
		for (SysMenu sDept : list) {
			super.deleteById(sDept.getId());
		}
		return super.deleteById(id);
	}
}
