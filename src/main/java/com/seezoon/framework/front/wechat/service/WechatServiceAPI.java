package com.seezoon.framework.front.wechat.service;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.beust.jcommander.internal.Maps;
import com.seezoon.framework.common.context.exception.ServiceException;
import com.seezoon.framework.common.http.HttpRequestUtils;
import com.seezoon.framework.common.service.BaseService;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.front.wechat.dto.AuthAccessToken;
import com.seezoon.framework.front.wechat.dto.JsApiTicket;
import com.seezoon.framework.front.wechat.dto.JsCode2session;
import com.seezoon.framework.front.wechat.dto.QrPayCallbackReturn;
import com.seezoon.framework.front.wechat.dto.Token;
import com.seezoon.framework.front.wechat.dto.UnifiedOrder;
import com.seezoon.framework.front.wechat.dto.UnifiedOrderResult;
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
	public static String mappId = WechatConfig.getMappID();
	public static String mappsecret = WechatConfig.getMappsecret();
	public static String mchId = WechatConfig.getMchId();
	public static String notifyUrl = WechatConfig.getNotifyUrl();
	public static String mchKey = WechatConfig.getMchKey();
	public static String spbillCreateIp = WechatConfig.getSpbillCreateIp();

	
	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	/**
	 * h5 auth code  换取auth token
	 * @param code
	 * @return
	 */
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
	/**
	 * author token 换取用户信息
	 * @param accessToken
	 * @return
	 */
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
	/**
	 * 获取接口操作token
	 * @return
	 */
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
	
	/**
	 * h5 js api ticket
	 * 
	 * @return
	 */
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
	/**
	 * h5 需要js 的签名参数
	 * @param url
	 * @return
	 */
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
	/*
	 * js 支付
	 */
	public Map<String,Object> jsPay(String body,String out_trade_no,Integer total_fee,String openid,String attach) {
		Assert.hasLength(openid,"openid 为空");
		UnifiedOrder unifiedOrderParams = this.buildUnifiedOrderParams(body, out_trade_no, total_fee, attach, "JSAPI");
		//js 支付openid 必传
		unifiedOrderParams.setOpenid(openid);
		UnifiedOrderResult unifiedOrder = this.unifiedOrder(unifiedOrderParams);
		return this.getJsPayParams(unifiedOrder.getPrepay_id());
	}
	/*
	 * 小程序 支付
	 */
	public Map<String,Object> mpay(String body,String out_trade_no,Integer total_fee,String openid,String attach) {
		Assert.hasLength(openid,"openid 为空");
		UnifiedOrder unifiedOrderParams = this.buildUnifiedOrderParams(body, out_trade_no, total_fee, attach, "JSAPI");
		unifiedOrderParams.setAppid(mappId);
		//小程序支付openid 必传
		unifiedOrderParams.setOpenid(openid);
		UnifiedOrderResult unifiedOrder = this.unifiedOrder(unifiedOrderParams);
		return this.getMPayParams(unifiedOrder.getPrepay_id());
	}
	/**
	 * 扫码支付模式1 二维码有效期2个小时
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @param attach
	 * @return
	 */
	public String qrCodePay1(String body,String out_trade_no,Integer total_fee,String attach) {
		UnifiedOrder unifiedOrderParams = this.buildUnifiedOrderParams(body, out_trade_no, total_fee, attach, "NATIVE");
		//扫码模式 product_id 必传
		unifiedOrderParams.setProduct_id(out_trade_no);
		UnifiedOrderResult unifiedOrder = this.unifiedOrder(unifiedOrderParams);
		return unifiedOrder.getCode_url();
	}
	/**
	 * 正式使用此方法需要根据产品id 查询出统一下单的参数
	 * @param product_id
	 * @return
	 */
	public String qrCodePay2(String product_id,String openid,String nonce_str) {
		UnifiedOrder unifiedOrderParams = this.buildUnifiedOrderParams("测试扫码支付", RandomStringUtils.randomNumeric(20), 1, "demoPayHandler", "NATIVE");
		//扫码模式 product_id 必传
		unifiedOrderParams.setProduct_id(product_id);
		UnifiedOrderResult unifiedOrder = this.unifiedOrder(unifiedOrderParams);
		QrPayCallbackReturn qrPayCallbackReturn = new QrPayCallbackReturn();
		qrPayCallbackReturn.setAppid(appId);
		qrPayCallbackReturn.setMch_id(mchId);
		qrPayCallbackReturn.setNonce_str(nonce_str);
		qrPayCallbackReturn.setPrepay_id(unifiedOrder.getPrepay_id());
		qrPayCallbackReturn.setResult_code("SUCCESS");
		qrPayCallbackReturn.setReturn_code("SUCCESS");
		TreeMap<String, Object> bean2map = WxUtils.bean2map(qrPayCallbackReturn);
		String sortStr = WxUtils.createSortStr(bean2map) + "&key=" + mchKey;
		String sign = CodecUtils.md5(sortStr).toUpperCase();
		qrPayCallbackReturn.setSign(sign);
		return WxUtils.beanToXml(qrPayCallbackReturn);
	}
	/**
	 * 模式二线获取支付二维码
	 * @param product_id
	 * @return
	 */
	public String getPayQrCode(String product_id) {
		Assert.hasLength(product_id,"product_id 为null");
		TreeMap<String,Object> params = new TreeMap<>();
		params.put("appid", appId);
		params.put("mch_id", mchId);
		long createTimestamp = WxUtils.createTimestamp();
		params.put("time_stamp", createTimestamp);
		String noncestr = WxUtils.createNoncestr();
		params.put("nonce_str", noncestr);
		params.put("product_id", product_id);
		String sortStr = WxUtils.createSortStr(params) + "&key=" + mchKey;
		String sign = CodecUtils.md5(sortStr).toUpperCase();
		params.put("sign", sign);
		return "weixin://wxpay/bizpayurl?appid="+appId+"&mch_id="+mchId+"&nonce_str=" +noncestr+ "&product_id="+product_id+"&time_stamp="+createTimestamp+"&sign="+sign;
	}
	/**
	 * 统一下单基本参数
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @param attach
	 * @param trade_type
	 * @return
	 */
	private UnifiedOrder buildUnifiedOrderParams(String body,String out_trade_no,Integer total_fee,String attach,String trade_type) {
		Assert.hasLength(body,"商品描述body 为空");
		Assert.hasLength(out_trade_no,"订单号out_trade_no 为空");
		Assert.notNull(total_fee,"商品金额total_fee 为空");
		Assert.hasLength(trade_type,"trade_type 为空");
		Assert.hasLength(attach,"回调类的spring bean 名称");
		UnifiedOrder unifiedOrder = new UnifiedOrder();
		unifiedOrder.setAppid(appId);
		unifiedOrder.setMch_id(mchId);
		unifiedOrder.setNonce_str(WxUtils.createNoncestr());
		unifiedOrder.setTrade_type(trade_type);
		unifiedOrder.setBody(body);
		unifiedOrder.setOut_trade_no(out_trade_no);
		unifiedOrder.setTotal_fee(total_fee);
		unifiedOrder.setSpbill_create_ip(spbillCreateIp);
		unifiedOrder.setAttach(attach);
		unifiedOrder.setNotify_url(notifyUrl);
		return unifiedOrder;
	}
	/**
	 * 统一下单
	 * @param unifiedOrder
	 * @return
	 */
	public UnifiedOrderResult unifiedOrder(UnifiedOrder unifiedOrder) {
		String unifiedOrderSign = getUnifiedOrderSign(unifiedOrder);
		unifiedOrder.setSign(unifiedOrderSign);
		String xml = WxUtils.beanToXml(unifiedOrder);
		String xmlResult = HttpRequestUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
		UnifiedOrderResult unifiedOrderResult = WxUtils.xmlToBean(xmlResult, UnifiedOrderResult.class);
		if (!unifiedOrderResult.isSuccess()) {
			throw new ServiceException(unifiedOrderResult.getReturn_msg() + StringUtils.trimToEmpty(unifiedOrderResult.getErr_code_des()));
		}
		return unifiedOrderResult;
	}
	public Map<String,Object> getJsPayParams(String prepay_id){
		Assert.hasLength(prepay_id,"prepay_id 为空");
		TreeMap<String,Object> jsParams = new TreeMap<>();
		jsParams.put("appId", appId);
		jsParams.put("timeStamp", String.valueOf(WxUtils.createTimestamp()));
		jsParams.put("nonceStr", WxUtils.createNoncestr());
		jsParams.put("package", "prepay_id=" + prepay_id);
		jsParams.put("signType", "MD5");
		String sortStr = WxUtils.createSortStr(jsParams) + "&key=" +mchKey;
		String paySign = CodecUtils.md5(sortStr).toUpperCase();
		jsParams.put("paySign", paySign);
		// package 为js 关键字需要转换下
		jsParams.put("_package", "prepay_id=" + prepay_id);
		return jsParams;
	}
	public Map<String,Object> getMPayParams(String prepay_id){
		Assert.hasLength(prepay_id,"prepay_id 为空");
		TreeMap<String,Object> jsParams = new TreeMap<>();
		jsParams.put("appId", mappId);
		jsParams.put("timeStamp", String.valueOf(WxUtils.createTimestamp()));
		jsParams.put("nonceStr", WxUtils.createNoncestr());
		jsParams.put("package", "prepay_id=" + prepay_id);
		jsParams.put("signType", "MD5");
		String sortStr = WxUtils.createSortStr(jsParams) + "&key=" +mchKey;
		String paySign = CodecUtils.md5(sortStr).toUpperCase();
		jsParams.put("paySign", paySign);
		// package 为js 关键字需要转换下
		jsParams.put("_package", "prepay_id=" + prepay_id);
		return jsParams;
	}
	private String getUnifiedOrderSign(UnifiedOrder unifiedOrder) {
		try {
			String sortStr = WxUtils.createSortStr(WxUtils.bean2map(unifiedOrder));
			sortStr += "&key=" +mchKey;
			String md5Upper = CodecUtils.md5(sortStr).toUpperCase();
			return md5Upper;
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		} 
	}
	/**
	 * 支付回调，扫码回调验证签名
	 * @param payResult
	 * @return
	 */
	public boolean checkCallbackSign(String resultXml) {
		Assert.hasLength(resultXml,"验证签名数据为空");
		TreeMap<String, Object> xml2map = WxUtils.xml2map(resultXml);
		//验证签名
		String sign = (String) xml2map.remove("sign");
		String sortStr = WxUtils.createSortStr(xml2map) + "&key=" +mchKey;
		String newSign = CodecUtils.md5(sortStr).toUpperCase();
		return newSign.equals(sign);
	}
	
	/**
	 * 小程序登录获取
	 * @param code
	 * @return
	 */
	public JsCode2session jscode2session(String code) {
		Assert.hasLength(code,"code为空");
		Map<String,String> params = Maps.newHashMap();
		params.put("appid",mappId);
		params.put("secret",mappsecret);
		params.put("js_code",code);
		params.put("grant_type","authorization_code");
		JsCode2session code2session = HttpRequestUtils.doGet("https://api.weixin.qq.com/sns/jscode2session", params, JsCode2session.class);
		if (code2session.isSuccess())  {
			return code2session;
		} else {
			throw new ServiceException(code2session.getErrmsg());
		}
	}
}
