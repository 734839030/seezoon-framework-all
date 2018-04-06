package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysMenuDao;
import com.seezoon.framework.modules.system.entity.SysMenu;

@Service
public class SysMenuService extends CrudService<SysMenuDao, SysMenu> {

	public List<SysMenu> findByParentIds(String parentIds){
		Assert.hasLength(parentIds,"parentIds为空");
		SysMenu sysMenu = new SysMenu();
		sysMenu.setParentIds(parentIds);
		return this.findList(sysMenu);
	}
	@Override
	public int deleteById(Serializable id) {
		//删除下级部门
		SysMenu sysMenu = this.findById(id);
		Assert.notNull(sysMenu,"菜单为空");
		List<SysMenu> list = this.findByParentIds(sysMenu.getParentIds() + sysMenu.getId());
		for (SysMenu sDept : list) {
			super.deleteById(sDept.getId());
		}
		return super.deleteById(id);
	}
	
	public void batchSave(List<SysMenu> list) {
		Assert.notEmpty(list,"批量数据为空");
		for (SysMenu sysMenu : list) {
			this.updateSelective(sysMenu);
		}
	}
}
