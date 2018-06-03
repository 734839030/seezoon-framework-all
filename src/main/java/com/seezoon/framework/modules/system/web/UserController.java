package com.seezoon.framework.modules.system.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.file.FileHandlerFactory;
import com.seezoon.framework.common.utils.BtRemoteValidateResult;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysLoginLog;
import com.seezoon.framework.modules.system.entity.SysMenu;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysLoginLogService;
import com.seezoon.framework.modules.system.service.SysMenuService;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.shiro.User;

/**
 * 综合用户信息处理
 * 
 * @author hdf 2018年4月14日
 */
@RestController
@RequestMapping("${admin.path}/user")
public class UserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysLoginLogService sysLoginLogService;

	@PostMapping("/getUserMenus.do")
	public ResponeModel getUserMenus() {
		User user = ShiroUtils.getUser();
		String userId = user.getUserId();
		List<SysMenu> menus = null;
		// 系统管理员，拥有最高权限
		if (ShiroUtils.isSuperAdmin()) {
			menus = sysMenuService.findShowMenuAll();
		} else {
			menus = sysMenuService.findShowMenuByUserId(userId);
		}
		return ResponeModel.ok(menus);
	}

	@PostMapping("/logout.do")
	public ResponeModel logout() {
		ShiroUtils.logout();
		return ResponeModel.ok();
	}

	@PostMapping("/getUserInfo.do")
	public ResponeModel getUserInfo() {
		String userId = ShiroUtils.getUserId();
		SysUser sysUser = sysUserService.findById(userId);
		Assert.notNull(sysUser, "用户存在");
		sysUser.setPhotoFullUrl(FileHandlerFactory.getFullUrl(sysUser.getPhoto()));
		SysLoginLog lastLoginInfo = sysLoginLogService.findLastLoginInfo(userId);
		if (null != lastLoginInfo) {
			sysUser.setLastLoginIp(lastLoginInfo.getIp());
			sysUser.setLastLoginTime(lastLoginInfo.getLoginTime());
			sysUser.setLastLoginArea(lastLoginInfo.getArea());
		}
		return ResponeModel.ok(sysUser);
	}

	@PostMapping("/updateInfo.do")
	public ResponeModel updateInfo(SysUser user) {
		Assert.hasLength(user.getName(), "姓名为空");
		SysUser sysUser = new SysUser();
		sysUser.setPhoto(user.getPhoto());
		sysUser.setName(user.getName());
		sysUser.setEmail(user.getEmail());
		sysUser.setMobile(user.getMobile());
		sysUser.setId(ShiroUtils.getUserId());
		int cnt = sysUserService.updateSelective(sysUser);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/checkPassword.do")
	public BtRemoteValidateResult checkPassword(@RequestParam String oldPassword) {
		return BtRemoteValidateResult.valid(sysUserService.validatePwd(oldPassword, ShiroUtils.getUserId()));
	}

	@PostMapping("/updatePwd.do")
	public ResponeModel updatePwd(@RequestParam String password, @RequestParam String oldPassword) {
		String userId = ShiroUtils.getUserId();
		if (!sysUserService.validatePwd(oldPassword, userId)) {
			return ResponeModel.error("原密码错误");
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(userId);
		sysUser.setPassword(password);
		int cnt = sysUserService.updateSelective(sysUser);
		return ResponeModel.ok(cnt);
	}

}
