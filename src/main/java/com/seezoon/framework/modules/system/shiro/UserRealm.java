package com.seezoon.framework.modules.system.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.seezoon.framework.common.Constants;
import com.seezoon.framework.modules.system.entity.SysMenu;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysMenuService;
import com.seezoon.framework.modules.system.service.SysRoleService;
import com.seezoon.framework.modules.system.service.SysUserService;

/**
 * 认证
 * 
 */
//@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	/**
	 * 授权(验证权限时调用) 配置了和会话时间相同的缓存
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		String userId = user.getUserId();
		Set<String> permsSet = new HashSet<>();
		Set<String> roleSet = new HashSet<>();
		List<SysMenu> menus = null;
		// 系统管理员，拥有最高权限
		if (ShiroUtils.isSuperAdmin()) {
			menus = sysMenuService.findShowMenuAll();
		} else {
			menus = sysMenuService.findShowMenuByUserId(userId);
		}
		// 用户权限列表
		for (SysMenu menu : menus) {
			if (StringUtils.isNotEmpty(menu.getPermission())) {
				permsSet.addAll(Arrays.asList(StringUtils.split(menu.getPermission().trim(), Constants.SEPARATOR)));
			}
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		List<SysRole> roles = user.getRoles();
		if (roles != null && !roles.isEmpty()) {
			for (SysRole sysRole : roles) {
				roleSet.add(sysRole.getName());
			}
		}
		info.setRoles(roleSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 查询用户信息
		SysUser sysUser = sysUserService.findByLoginName(token.getUsername());
		// 账号不存在
		if (sysUser == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 禁用状态
		if (SysUser.STATUS_STOP.equals(sysUser.getStatus())) {
			throw new LockedAccountException("账号已被锁定");
		}
		User user = new User(sysUser.getId(), sysUser.getDeptId(), sysUser.getDeptName(), sysUser.getLoginName(),
				sysUser.getName());
		//放入角色
		user.setRoles(sysRoleService.findByUserId(user.getUserId()));
		//放入认证通过数据
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, sysUser.getPassword(),
				ByteSource.Util.bytes(sysUser.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
