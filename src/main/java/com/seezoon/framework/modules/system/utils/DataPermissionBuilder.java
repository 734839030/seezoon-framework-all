package com.seezoon.framework.modules.system.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;
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
	/**
	 * 自定义部门
	 */
	private static final String CUSTOM_DEPT = "4";

	public static String build(String tableAlias) {
		User user = ShiroUtils.getUser();
		List<SysRole> roles = user.getRoles();
		String currentUser = user.getUserId();
		String deptId = user.getDeptId();
		StringBuilder sb = new StringBuilder();
		Set<String> mergeDataScope =  Sets.newHashSet();
		for (SysRole role : roles) {
			mergeDataScope.add(role.getDataScope());
		}
		//多个角色数据权限取OR ,包含关系自动跳过
		if (!ShiroUtils.isSuperAdmin() && null != roles && !roles.isEmpty() && !mergeDataScope.contains(ALL)) {
			for (SysRole role : roles) {
				String dataScope = role.getDataScope();
				if (CURRENT_DEPT.equals(dataScope) && mergeDataScope.contains(CURRENT_DEPT_LOW_LEVEL)) {
					continue;
				}
				if (CURRENT_USER.equals(dataScope) && mergeDataScope.containsAll(Arrays.asList(new String[]{CURRENT_DEPT_LOW_LEVEL,CURRENT_DEPT}))) {
					continue;
				}
				sb.append(" or ");
				if (StringUtils.isNotEmpty(tableAlias)) {
					sb.append(tableAlias).append(".");
				}
				// 因为mysql update delete 子查询限制，最好对子查询取别名
				if (CURRENT_DEPT_LOW_LEVEL.equals(dataScope)) {//本部门及以下
					sb.append("create_by ").append("in (select sf_alias.id in (select su.id from sys_user su left join sys_dept sd on su.dept_id = sd.id  where sd.id= '")
					.append(deptId).append("' or sd.parent_ids like ").append("'%").append(deptId).append("%') sf_alias) ");
				} else if (CURRENT_DEPT.equals(dataScope)) {//本部门
					sb.append("create_by ").append("in (select sf_alias.id (select id from sys_user where dept_id = '").append(deptId).append("') sf_alias) ");
				} else if (CURRENT_USER.equals(dataScope)) {//本人
					sb.append("create_by = ").append("'").append(currentUser).append("'");
				} else if (CUSTOM_DEPT.equals(dataScope)) {//自定义部门
				   sb.append("create_by ").append("in (select sf_alias.id (select su.id from sys_user su where su.dept_id in (select dept_id from sys_role_dept where role_id = '").append(role.getId()).append("')) sf_alias) ");
				}
			}
			//去掉最前面的or
			String psql = sb.toString().substring(4);
			StringBuilder allSql = new StringBuilder();
			allSql.append(" and (")
			.append(psql)
			.append(")");
			return allSql.toString();
		}
		return StringUtils.EMPTY;
	}
}
