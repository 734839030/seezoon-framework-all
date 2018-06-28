package com.seezoon.framework.front.wechat.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.front.session.FrontSubject;
import com.seezoon.framework.front.session.FrontUser;
import com.seezoon.framework.front.wechat.dto.AuthAccessToken;
import com.seezoon.framework.front.wechat.dto.UserInfo;
import com.seezoon.framework.front.wechat.service.WechatServiceAPI;
import com.seezoon.framework.modules.wechat.entity.WechatUserInfo;
import com.seezoon.framework.modules.wechat.service.WechatUserInfoService;

@RestController
@RequestMapping("${front.path}/wechat")
public class WechatLoginController extends BaseController{
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOperations;
	@Autowired
	private WechatServiceAPI wechatUserInfoServiceAPI;
	@Autowired
	private WechatUserInfoService wechatUserInfoService;
	@RequestMapping("/auth2Login.do")
	public void auth2Login(@RequestParam String code,@RequestParam String state,HttpServletResponse response,HttpSession session) throws IOException {
		String redirectUrl = valueOperations.get(state);
		Assert.hasLength(redirectUrl,"redirectUrl地址为空");
		AuthAccessToken accessToken = wechatUserInfoServiceAPI.getUserInfoByCode(code);
		FrontUser frontUser = new FrontUser();
		WechatUserInfo wechatUserInfo = new WechatUserInfo();
		if ("snsapi_base".equals(accessToken.getScope())) {//不需要用户信息
			frontUser.setUserId(accessToken.getOpenid());
			wechatUserInfo.setOpenid(accessToken.getOpenid());
		} else if ("snsapi_userinfo".equals(accessToken.getScope())) {
			UserInfo userinfo = wechatUserInfoServiceAPI.userinfo(accessToken);
			frontUser.setUserId(userinfo.getOpenid());
			frontUser.setName(userinfo.getNickname());
			wechatUserInfo.setOpenid(userinfo.getOpenid());
			wechatUserInfo.setNickname(userinfo.getNickname());
			wechatUserInfo.setSex(userinfo.getSex());
			wechatUserInfo.setProvince(userinfo.getProvince());
			wechatUserInfo.setCity(userinfo.getCity());
			wechatUserInfo.setCountry(userinfo.getCountry());
			wechatUserInfo.setHeadImgUrl(userinfo.getHeadimgurl());
			wechatUserInfo.setUnionid(userinfo.getUnionid());
		}
		WechatUserInfo dbWechatUserInfo = wechatUserInfoService.findByOpenId(wechatUserInfo.getOpenid());
		if (null == dbWechatUserInfo) {
			wechatUserInfoService.save(wechatUserInfo);
		} else {
			wechatUserInfo.setId(dbWechatUserInfo.getId());
			wechatUserInfoService.updateSelective(wechatUserInfo);
		}
		FrontSubject.putUserSession(session, frontUser);
		response.sendRedirect(redirectUrl);
	}
}
