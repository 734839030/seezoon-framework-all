package com.seezoon.framework.modules.system.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.Constants;
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
		//删除下级菜单
		SysMenu sysMenu = this.findById(id);
		Assert.notNull(sysMenu,"菜单为空");
		List<SysMenu> list = this.findByParentIds(sysMenu.getParentIds() + sysMenu.getId());
		for (SysMenu smenu : list) {
			this.d.deleteRoleMenuByMenuId(smenu.getId());
			super.deleteById(smenu.getId());
		}
		this.d.deleteRoleMenuByMenuId(id);
		return super.deleteById(id);
	}
	
	public void batchSave(List<SysMenu> list) {
		Assert.notEmpty(list,"批量数据为空");
		for (SysMenu sysMenu : list) {
			this.updateSelective(sysMenu);
		}
	}
	public List<SysMenu> findByRoleId(String roleId){
		if (StringUtils.isEmpty(roleId)) {
			return this.findList(null);
		}
		return this.d.findByRoleId(roleId);
	}
	public List<SysMenu> findShowMenuByUserId(String userId){
		Assert.hasLength(userId,"userId为空");
		return this.d.findByUserId(userId);
	}
	
	public List<SysMenu> findShowMenuAll(){
		SysMenu menu = new SysMenu();
		menu.setIsShow(Constants.YES);
		menu.setSortField("sort");
		menu.setDirection(Constants.ASC);
		return this.findList(menu);
	} 
}
