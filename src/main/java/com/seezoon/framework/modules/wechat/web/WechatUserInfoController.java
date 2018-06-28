package com.seezoon.framework.modules.wechat.web;

import java.io.Serializable;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.seezoon.framework.modules.wechat.entity.WechatUserInfo;
import com.seezoon.framework.modules.wechat.service.WechatUserInfoService;

/**
 * 用户信息controller
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-6-18 10:29:20
 */
@RestController
@RequestMapping("${admin.path}/wechat/userinfo")
public class WechatUserInfoController extends BaseController {

	@Autowired
	private WechatUserInfoService wechatUserInfoService;

	@RequiresPermissions("wechat:userinfo:qry")
	@PostMapping("/qryPage.do")
	public ResponeModel qryPage(WechatUserInfo wechatUserInfo) {
		PageInfo<WechatUserInfo> page = wechatUserInfoService.findByPage(wechatUserInfo, wechatUserInfo.getPage(), wechatUserInfo.getPageSize());
		return ResponeModel.ok(page);
	}
	@RequiresPermissions("wechat:userinfo:qry")
	@RequestMapping("/get.do")
	public ResponeModel get(@RequestParam Serializable id) {
		WechatUserInfo wechatUserInfo = wechatUserInfoService.findById(id);
		//富文本处理
		return ResponeModel.ok(wechatUserInfo);
	}
	@RequiresPermissions("wechat:userinfo:save")
	@PostMapping("/save.do")
	public ResponeModel save(@Validated WechatUserInfo wechatUserInfo, BindingResult bindingResult) {
		int cnt = wechatUserInfoService.save(wechatUserInfo);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("wechat:userinfo:update")
	@PostMapping("/update.do")
	public ResponeModel update(@Validated WechatUserInfo wechatUserInfo, BindingResult bindingResult) {
		int cnt = wechatUserInfoService.updateSelective(wechatUserInfo);
		return ResponeModel.ok(cnt);
	}
	@RequiresPermissions("wechat:userinfo:delete")
	@PostMapping("/delete.do")
	public ResponeModel delete(@RequestParam Serializable id) {
		int cnt = wechatUserInfoService.deleteById(id);
		return ResponeModel.ok(cnt);
	}
}
