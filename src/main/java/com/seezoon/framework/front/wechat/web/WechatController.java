package com.seezoon.framework.front.wechat.web;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.common.utils.SystemConfig;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.front.wechat.service.WechatServiceAPI;
import com.seezoon.framework.front.wechat.utils.WechatConfig;

/**
 * 微信交互操作类
 * @author hdf
 * 2018年6月18日
 */
@RestController
@RequestMapping("/public/wechat")
public class WechatController extends BaseController{

	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOperations;
	@Autowired
	private WechatServiceAPI wechatServiceAPI;
	/**
	 * 微信授权
	 * @param redirectUrl 回调地址 不传则取reffer ，如果传需要js urlEncode  encodeURIComponent(url)
	 * @param scope  授权模式，snsapi_base snsapi_userinfo，不传默认为snsapi_userinfo
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping("/oauth2.do")
	public void oauth2(String redirectUrl,String scope,HttpServletRequest request,HttpServletResponse response) throws IOException {
		String referer = request.getHeader("referer"); 
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		String contextPath = request.getContextPath();
		int port = request.getServerPort();
		String portStr= ":" + port;
		if (port == 80 || port == 443) {
			portStr = "";
		}
		String resultUrl = referer;
		if (StringUtils.isNotEmpty(redirectUrl)) {
			resultUrl = redirectUrl;
		}
		String wechatRedirectUrl = scheme + "://" + serverName + portStr + contextPath + SystemConfig.getFrontPath() +"/wechat/auth2Login.do";
		logger.debug("oauth2 req param redirectUrl={},referer={},wechatRedirectUrl={}",redirectUrl,referer,wechatRedirectUrl);
		String sha1ResultUrl = CodecUtils.sha1(resultUrl);
		valueOperations.set(sha1ResultUrl, resultUrl, 1, TimeUnit.MINUTES);
		Assert.hasLength(resultUrl,"业务地址为空");
		if (StringUtils.isEmpty(scope)) {
			scope = "snsapi_userinfo";
		}
		String sendRedirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.getAppID() + "&redirect_uri=" + CodecUtils.urlEncode(wechatRedirectUrl) + "&response_type=code&scope=" + scope +"&state=" + sha1ResultUrl +"#wechat_redirect";
		response.sendRedirect(sendRedirectUrl);
	}
	@RequestMapping("/getJsConfig.do")
	public ResponeModel getJsConfig(HttpServletRequest request) {
		String referer = request.getHeader("referer"); 
		return ResponeModel.ok(wechatServiceAPI.getJsConfig(referer));
	}
}
