package com.seezoon.framework.modules.system.utils;

import java.util.List;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.shiro.User;

/**
 * 后端数据权限处理
 * 
 * @author hdf 2018年5月18日
 */
public class DataPermissionBuilder {

	
	/**
	 * 0:全部
	   1:本部门及以下
	   2:本部门
	   3:本人
	 * @return
	 */
	public static String build() {
		User user = ShiroUtils.getUser();
		List<SysRole> roles = user.getRoles();
		if (!ShiroUtils.isSuperAdmin() && null != roles && !roles.isEmpty()) {
		}
		return null;
	}
}
