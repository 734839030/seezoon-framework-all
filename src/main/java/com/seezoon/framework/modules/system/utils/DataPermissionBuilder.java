package com.seezoon.framework.modules.system.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
	 * 全部
	 */
	private static final String ALL = "0";
	/**
	 * 本部门及以下
	 */
	private static final String CURRENT_DEPT_LOW_LEVEL = "1";
	/**
	 * 本部门
	 */
	private static final String CURRENT_DEPT = "2";
	/**
	 * 本人
	 */
	private static final String CURRENT_USER = "3";

	public static String build(String tableAlias) {
		User user = ShiroUtils.getUser();
		List<SysRole> roles = user.getRoles();
		String scope = ALL;
		if (!ShiroUtils.isSuperAdmin() && null != roles && !roles.isEmpty()) {
			for (SysRole role : roles) {
				String dataScope = role.getDataScope();
				if (StringUtils.isNotEmpty(dataScope) && dataScope.compareTo(scope) > 0) {
					scope = role.getDataScope();
				}
			}
			if (ALL.equals(scope)) {
				return StringUtils.EMPTY;
			}
			String currentUser = user.getUserId();
			String deptId = user.getDeptId();
			StringBuilder sb = new StringBuilder();
			sb.append(" and ");
			if (StringUtils.isNotEmpty(tableAlias)) {
				sb.append(tableAlias).append(".");
			}
			if (CURRENT_DEPT_LOW_LEVEL.equals(scope)) {//本部门及以下
				sb.append("create_by ").append("in (select su.id from sys_user su left join sys_dept sd on su.dept_id = sd.id  where sd.id= '")
				.append(deptId).append("' or sd.parent_ids like ").append("'%").append(deptId).append("%')");
			} else if (CURRENT_DEPT.equals(scope)) {//本部门
				sb.append("create_by ").append("in (select id from sys_user where dept_id = '").append(deptId).append("')");
			} else if (CURRENT_USER.equals(scope)) {//本人
				sb.append("create_by = ").append("'").append(currentUser).append("'");
			}
			return sb.toString();
		}
		return StringUtils.EMPTY;
	}
}
