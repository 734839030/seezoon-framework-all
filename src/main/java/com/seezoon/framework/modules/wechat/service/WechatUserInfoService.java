package com.seezoon.framework.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.seezoon.framework.common.service.CrudService;
import com.seezoon.framework.modules.wechat.dao.WechatUserInfoDao;
import com.seezoon.framework.modules.wechat.entity.WechatUserInfo;

/**
 * 用户信息Service
 * Copyright &copy; 2018 powered by huangdf, All rights reserved.
 * @author hdf 2018-6-18 10:29:20
 */
@Service
public class WechatUserInfoService extends CrudService<WechatUserInfoDao, WechatUserInfo>{

	public WechatUserInfo findByOpenId(String openId) {
		Assert.hasLength(openId,"openId 为空");
		WechatUserInfo wechatUserInfo = new WechatUserInfo();
		wechatUserInfo.setOpenid(openId);
		List<WechatUserInfo> list = this.findList(wechatUserInfo);
		return list.size() > 0 ? list.get(0):null;
	}
	
}
