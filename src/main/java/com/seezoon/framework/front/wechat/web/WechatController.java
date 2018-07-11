package com.seezoon.framework.front.wechat.web;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.context.support.SpringContextHolder;
import com.seezoon.framework.common.utils.CodecUtils;
import com.seezoon.framework.common.utils.SystemConfig;
import com.seezoon.framework.common.web.BaseController;
import com.seezoon.framework.front.wechat.dto.BaseWechatPayReturnDto;
import com.seezoon.framework.front.wechat.dto.PayResult;
import com.seezoon.framework.front.wechat.service.PayResultHandler;
import com.seezoon.framework.front.wechat.service.WechatServiceAPI;
import com.seezoon.framework.front.wechat.utils.WechatConfig;
import com.seezoon.framework.front.wechat.utils.WxUtils;

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
//改为配置化，因为用花生壳工具在本地开发，无法真实获取
//		String scheme = request.getScheme();
//		String serverName = request.getServerName();
//		String contextPath = request.getContextPath();
//		int port = request.getServerPort();
//		String portStr= ":" + port;
//		if (port == 80 || port == 443) {
//			portStr = "";
//		}
		String resultUrl = referer;
		if (StringUtils.isNotEmpty(redirectUrl)) {
			resultUrl = redirectUrl;
		}
		//String wechatRedirectUrl = scheme + "://" + serverName + portStr + contextPath + SystemConfig.getFrontPath() +"/wechat/auth2Login.do";
		String wechatRedirectUrl =  WechatConfig.getCallBackBasePath() + SystemConfig.getFrontPath() +"/wechat/auth2Login.do";
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
	/**
	 * 扫码支付回调
	 */
	@RequestMapping("/qrPayCallback.do")
	public void qrPayCallback(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletInputStream inputStream = request.getInputStream();
			String qrPayCallbackXml = IOUtils.toString(inputStream, "UTF-8");
			logger.info("qrPayCallbackXml:{}",qrPayCallbackXml);
			//验证签名 用原始数据验签名
			if (!wechatServiceAPI.checkCallbackSign(qrPayCallbackXml)) {
				logger.warn("qrPayCallback checkSign unpass");
				return ;
			}
			TreeMap<String, Object> xml2map = WxUtils.xml2map(qrPayCallbackXml);
			String product_id = (String) xml2map.get("product_id	");
			String openid = (String) xml2map.get("openid	");
			String nonce_str = (String) xml2map.get("nonce_str");
			String returnXml = wechatServiceAPI.qrCodePay2(product_id,openid,nonce_str);
			WxUtils.outPrint(response, returnXml);
		} catch (IOException e) {
			logger.error("qrPayCallback","获取扫码回调数据失败");
		}
	}
	/**
	 * 统一支付回调
	 * @param request
	 * @param response
	 */
	@RequestMapping("/payResult.do")
	public void payResult(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletInputStream inputStream = request.getInputStream();
			String payResultXml = IOUtils.toString(inputStream, "UTF-8");
			logger.info("payResultXml:{}",payResultXml);
			//解析数据
			PayResult payResult = WxUtils.xmlToBean(payResultXml, PayResult.class);
			logger.info("payResult handler:{},openid:{},out_trade_no:{},transaction_id:{},total_fee:{}",payResult.getAttach(),payResult.getOpenid(),payResult.getOut_trade_no(),payResult.getTransaction_id(),payResult.getTotal_fee());
			//验证签名 用原始数据验签名
			if (!wechatServiceAPI.checkCallbackSign(payResultXml)) {
				logger.warn("payResult checkPayResultSign unpass");
				return ;
			}
			PayResultHandler handler = SpringContextHolder.getBean(payResult.getAttach(),PayResultHandler.class);
			if (handler.handle(payResult)) {
				BaseWechatPayReturnDto payReturnDto = new BaseWechatPayReturnDto();
				payReturnDto.setReturn_code("SUCCESS");
				payReturnDto.setReturn_msg("OK");
				String returnXml = WxUtils.beanToXml(payReturnDto);
				WxUtils.outPrint(response, returnXml);
			}
		} catch (IOException e) {
			logger.error("payResult","获取回调数据失败");
		}
	}
}
