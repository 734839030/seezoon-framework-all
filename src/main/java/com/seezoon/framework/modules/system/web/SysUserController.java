package com.seezoon.framework.modules.system.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.modules.system.entity.SysUser;
import com.seezoon.framework.modules.system.service.SysUserService;

@RestController
@RequestMapping("${admin.path}/sys/user")
public class SysUserController extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(SysUser sysUser) {
		PageInfo<SysUser> page = sysUserService.findByPage(sysUser, sysUser.getPage(), sysUser.getPageSize());
		return ResponeModel.ok(page);
	}

	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		SysUser sysUser = sysUserService.findById(id);
		return ResponeModel.ok(sysUser);
	}

	@PostMapping("/save.do")
	public ResponeModel save(@Validated SysUser sysUser, BindingResult bindingResult) {
		int cnt = sysUserService.save(sysUser);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/update.do")
	public ResponeModel update(@Validated SysUser sysUser, BindingResult bindingResult) {
		int cnt = sysUserService.updateSelective(sysUser);
		return ResponeModel.ok(cnt);
	}

	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = sysUserService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
	
	@PostMapping("/checkLoginName.do")
	public Map<String,Object> checkLoginName(@RequestParam(required=false) String id,@RequestParam  String loginName){
		Map<String,Object> result = new HashMap<>();
		if(StringUtils.isEmpty(loginName)) {
			result.put("valid",Boolean.TRUE);
		} else {
			SysUser sysUser = sysUserService.findByLoginName(loginName);
			result.put("valid", sysUser == null || sysUser.getId().equals(id));
		}
		return result;
	}
	@PostMapping("/setStatus.do")
	public ResponeModel setStatus(@RequestParam String id, @RequestParam String status) {
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setStatus(status);
		int cnt = this.sysUserService.updateSelective(sysUser);
		return ResponeModel.ok(cnt);
	}
	
}
