package com.seezoon.framework.modules.system.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.system.dao.SysRoleDao;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysRoleMenu;

@Service
public class SysRoleService extends CrudService<SysRoleDao, SysRole> {
	
	@Override
	public int save(SysRole t) {
		int cnt = super.save(t);
		this.saveRoleMenus(t.getId(), t.getMenuIds());
		return cnt;
	}

	@Override
	public int updateSelective(SysRole t) {
		this.d.deleteRoleMenuByRoleId(t.getId());
		this.saveRoleMenus(t.getId(), t.getMenuIds());
		return super.updateSelective(t);
	}

	private int saveRoleMenus(String roleId, List<String> menuIds) {
		if (null != menuIds && !menuIds.isEmpty()) {
			List<SysRoleMenu> roleMenus = Lists.newArrayList();
			for (String menuId : menuIds) {
				roleMenus.add(new SysRoleMenu(roleId, menuId));
			}
			return this.d.insertRoleMenu(roleMenus);
		}
		return 0;
	}

	public List<SysRole> findByUserId(String userId) {
		Assert.hasLength(userId,"userId 为空");
		return  this.d.findByUserId(userId);
	}
}
