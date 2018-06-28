package com.seezoon.framework.front.wechat.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.beust.jcommander.internal.Maps;
import com.seezoon.framework.common.context.exception.ServiceException;
import com.seezoon.framework.common.http.HttpRequestUtils;
import com.seezoon.framework.common.service.BaseService;
import com.seezoon.framework.front.wechat.dto.AuthAccessToken;
import com.seezoon.framework.front.wechat.dto.JsApiTicket;
import com.seezoon.framework.front.wechat.dto.Token;
import com.seezoon.framework.front.wechat.dto.UserInfo;
import com.seezoon.framework.front.wechat.utils.WechatConfig;
import com.seezoon.framework.front.wechat.utils.WxUtils;

/**
 * 微信相关API 封装
 * @author hdf
 * 2018年6月19日
 */
@Service
public class WechatServiceAPI extends BaseService{

	public static String appId = WechatConfig.getAppID();
	public static String appsecret = WechatConfig.getAppsecret();
	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	public AuthAccessToken getUserInfoByCode(String code) {
		Assert.hasLength(code,"code 为空");
		Map<String,String> params = Maps.newHashMap();
		params.put("appid",appId);
		params.put("secret",appsecret);
		params.put("code",code);
		params.put("grant_type","authorization_code");
		AuthAccessToken accessToken = HttpRequestUtils.doGet("https://api.weixin.qq.com/sns/oauth2/access_token", params, AuthAccessToken.class);
		if (accessToken.isSuccess()) {
			return accessToken;
		} else {
			throw new ServiceException(accessToken.getErrmsg());
		}
	}
	public UserInfo userinfo(AuthAccessToken accessToken) {
		Assert.notNull(accessToken,"accessToken 为null");
		Assert.hasLength(accessToken.getAccess_token(),"accessToken 为空");
		Assert.hasLength(accessToken.getOpenid(),"openid 为空");
		Map<String,String> params = Maps.newHashMap();
		params.put("access_token",accessToken.getAccess_token());
		params.put("openid",accessToken.getOpenid());
		params.put("lang","zh_CN");
		params.put("grant_type","authorization_code");
		UserInfo userInfo = HttpRequestUtils.doGet("https://api.weixin.qq.com/sns/userinfo", params,UserInfo.class);
		if (userInfo.isSuccess()) {
			return userInfo;
		} else {
			throw new ServiceException(accessToken.getErrmsg());
		}
	}
	public String getToken() {
		Map<String,String> params = Maps.newHashMap();
		params.put("grant_type","client_credential");
		params.put("appid",appId);
		params.put("secret",appsecret);
		String cachedToken = valueOperations.get("wx_token");
		Long expire = valueOperations.getOperations().getExpire("wx_token", TimeUnit.SECONDS);
		if (StringUtils.isNotEmpty(cachedToken) && expire > 3600) {//缓存两小时，剩余时间小于1小时就重新拿
			return cachedToken;
		}  else {
			Token token = HttpRequestUtils.doGet("https://api.weixin.qq.com/cgi-bin/token", params,Token.class);
			if (token.isSuccess())  {
				valueOperations.set("wx_token", token.getAccess_token(),7200,TimeUnit.SECONDS);
				return token.getAccess_token();
			} else {
				throw new ServiceException(token.getErrmsg());
			}
		}
	}
	public String getJsApiTicket() {
		String token = this.getToken();
		Assert.hasLength(token,"token 为null");
		Map<String,String> params = Maps.newHashMap();
		params.put("access_token",token);
		params.put("type","jsapi");
		String cachedTicket = valueOperations.get("wx_js_ticket");
		Long expire = valueOperations.getOperations().getExpire("wx_js_ticket", TimeUnit.SECONDS);
		if (StringUtils.isNotEmpty(cachedTicket) && expire > 3600) {//缓存两小时，剩余时间小于1小时就重新拿
			return cachedTicket;
		}  else {
			JsApiTicket jsApiTicket = HttpRequestUtils.doGet("https://api.weixin.qq.com/cgi-bin/ticket/getticket", params,JsApiTicket.class);
			if (jsApiTicket.isSuccess())  {
				valueOperations.set("wx_js_ticket", jsApiTicket.getTicket(),7200,TimeUnit.SECONDS);
				return jsApiTicket.getTicket();
			} else {
				throw new ServiceException(jsApiTicket.getErrmsg());
			}
		}
	}
	public Map<String,Object> getJsConfig(String url){
		Assert.hasLength(url,"url 为null");
		String noncestr = WxUtils.createNoncestr();
		long timestamp = WxUtils.createTimestamp();
		String jsapiTicket = getJsApiTicket();
		String signature = WxUtils.jsSignature(noncestr, timestamp, jsapiTicket, url);
		Map<String,Object> config = Maps.newHashMap();
		config.put("noncestr", noncestr);
		config.put("timestamp", timestamp);
		config.put("signature", signature);
		config.put("appId", appId);
		return config;
	}
}
