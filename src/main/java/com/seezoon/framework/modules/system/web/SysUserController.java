package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.file.FileConfig;
import com.seezoon.framework.common.file.FileHandlerFactory;
import com.seezoon.framework.common.utils.BtRemoteValidateResult;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysRole;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.LoginSecurityService;
import com.seezoon.framework.modules.system.service.SysRoleService;
import com.seezoon.framework.modules.system.service.SysUserService;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;

@RestController
@RequestMapping("${admin.path}/sys/user")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private LoginSecurityService loginSecurityService;
	@RequiresPermissions("sys:user:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysUser sysUser,HttpServletRequest request) {
		PageInfo<SysUser> page = sysUserService.findByPage(sysUser, sysUser.getPage(), sysUser.getPageSize());
		for (SysUser user: page.getList()) {
			user.setPhotoFullUrl(FileConfig.getFullUrl(user.getPhoto()));
		}
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("sys:user:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysUser sysUser = sysUserService.findById(id);
		Assert.notNull(sysUser,"用户不存在");
		sysUser.setPhotoFullUrl(FileHandlerFactory.getFullUrl(sysUser.getPhoto()));
		//用户所拥有的角色
		List<SysRole> roleList = sysRoleService.findByUserId(sysUser.getId());
		List<String> roleIds = Lists.newArrayList();
		for (SysRole sysRole:roleList) {
			roleIds.add(sysRole.getId());
		}
		sysUser.setRoleIds(roleIds);
		return ResponeModel.ok(sysUser);
	}
	@RequiresPermissions("sys:user:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysUser sysUser, BindingResult bindingResult) {
		int cnt = sysUserService.save(sysUser);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:user:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysUser sysUser, BindingResult bindingResult) {
		if (ShiroUtils.isSuperAdmin(sysUser.getId()) && SysUser.STATUS_STOP.equals(sysUser.getStatus())) {
			return ResponeModel.error("超级管理员不允许修改为禁用状态");
		}
		//密码为空则不更新
		sysUser.setPassword(StringUtils.trimToNull(sysUser.getPassword()));
		int cnt = sysUserService.updateUserRoleSelective(sysUser);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("sys:user:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam String id) {
		if (ShiroUtils.isSuperAdmin(id)) {
			return ResponeModel.error("超级管理员不允许删除");
		}
		if (ShiroUtils.getUserId().equals(id)) {
			return ResponeModel.error("自己不能删除自己");
		}
		int cnt = sysUserService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	
	@PostMapping("/checkLoginName.do")
	public BtRemoteValidateResult checkLoginName(@RequestParam(required=false) String id,@RequestParam  String loginName){
		if(StringUtils.isEmpty(loginName)) {
			return BtRemoteValidateResult.valid(Boolean.TRUE);
		} else {
			SysUser sysUser = sysUserService.findByLoginName(loginName);
			return BtRemoteValidateResult.valid(sysUser == null || sysUser.getId().equals(id));
		}
	}
	@RequiresPermissions("sys:user:update")
	@PostMapping("/setStatus.do")
	public ResponeModel setStatus(@RequestParam String id, @RequestParam String status) {
		if (ShiroUtils.isSuperAdmin(id)) {
			return ResponeModel.error("超级管理员不允许修改");
		}
		if (ShiroUtils.getUserId().equals(id)) {
			return ResponeModel.error("自己不能修改自己");
		}
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setStatus(status);
		this.sysUserService.updateSelective(sysUser);
		return ResponeModel.ok();
	}
	
	/**
	 * 账户锁定24小时 解锁
	 * 用户信息修改panel 双击图像解锁
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@PostMapping("/unlock.do")
	public ResponeModel setStatus(@RequestParam String id) {
		SysUser findById = sysUserService.findById(id);
		Assert.notNull(findById, "解锁用户不存在");
		loginSecurityService.unLock(findById.getLoginName());
		return ResponeModel.ok();
	}
}
