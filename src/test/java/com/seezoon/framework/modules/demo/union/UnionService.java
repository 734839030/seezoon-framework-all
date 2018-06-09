package com.seezoon.framework.modules.demo.union;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.seezoon.framework.common.context.exception.ServiceException;
import com.seezoon.framework.common.context.test.BaseJunitTest;
import com.seezoon.framework.common.http.HttpPoolClient;

public class UnionService extends BaseJunitTest{
	
	@Resource(name="redisTemplate")
	private ValueOperations<String, CookieStore> valueOperations;
	HttpPoolClient client = new HttpPoolClient();
	
	@Before
	public void before() {
		
	}
	/**
	 * https://uac.10010.com/portal/Service/SendMSG?callback=jQuery17205929719702722311_1528559748925&req_time=1528560335346&mobile=13249073372&_=1528560335347
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@Test
	public void sendSms() throws ParseException, Exception {
		CookieStore cookie = new BasicCookieStore() ;
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookie);
		MultiValueMap<String,String> params =  new LinkedMultiValueMap<>();
		params.put("req_time", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));
		params.put("_=", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));
		params.put("mobile", Lists.newArrayList("13249073372"));
		String url = UriComponentsBuilder.fromHttpUrl("https://uac.10010.com/portal/Service/SendMSG").queryParams(params).build().toUriString();
		HttpGet request = new HttpGet(url);
		request.setHeader("Referer", "https://uac.10010.com/portal/custLogin");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
		System.out.println("cookie:" + JSON.toJSONString(cookie));
		
	}
	/**
	 * https://uac.10010.com/portal/Service/MallLogin?callback=jQuery17205929719702722311_1528559748927&
	 * req_time=1528560369879&redirectURL=http%3A%2F%2Fwww.10010.com&userName=13249073372&
	 * password=844505&pwdType=02&productType=01&redirectType=06&rememberMe=1&_=1528560369879
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@Test
	public void login() throws ParseException, Exception {
		HttpClientContext httpClientContext = HttpClientContext.create();
		String mobile = "13249073372";
		MultiValueMap<String,String> params =  new LinkedMultiValueMap<>();
		params.put("req_time", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));
		params.put("_=", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));
		params.put("redirectURL", Lists.newArrayList("http%3A%2F%2Fwww.10010.com"));
		params.put("userName", Lists.newArrayList(mobile));
		params.put("password", Lists.newArrayList("950102"));
		params.put("pwdType", Lists.newArrayList("02"));
		params.put("productType", Lists.newArrayList("01"));
		params.put("redirectType", Lists.newArrayList("06"));
		params.put("rememberMe", Lists.newArrayList("1"));

		String url = UriComponentsBuilder.fromHttpUrl("https://uac.10010.com/portal/Service/MallLogin").queryParams(params).build().toUriString();
		HttpGet request = new HttpGet(url);
		request.setHeader("Referer", "https://uac.10010.com/portal/custLogin");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
		CookieStore cookie = httpClientContext.getCookieStore();
		System.out.println("cookie:" + JSON.toJSONString(cookie));
		valueOperations.set(mobile, cookie, 60, TimeUnit.MINUTES);
	}
	/**
	 * https://uac.10010.com/cust/infomgr/anonymousInfoAJAX
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@Test
	public void userInfo() throws Exception {
		String mobile = "13249073372";
		CookieStore cookieStore = valueOperations.get(mobile);
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookieStore);
		String url = UriComponentsBuilder.fromHttpUrl("https://uac.10010.com/cust/infomgr/anonymousInfoAJAX").build().toUriString();
		HttpPost request = new HttpPost(url);
		request.setHeader("Referer", "https://uac.10010.com/portal/custLogin");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		request.setHeader("Cookie", "mallcity=51|540; gipgeo=51|540; SHOP_PROV_CITY=; WT.mc_id=guangdong_ADKS_1708_baidupcpz_title; CaptchaCode=Z2mQP/olRVds/jQ9zzn48+/0wZ4L0Gf1FLC5AabeTwE=; WT_FPC=id=2599feebcb14e0c8d6a1528559123783:lv=1528559324782:ss=1528559123783; ckuuid=1776742a04c62f511da2683bbb15c4bb; unisecid=607025C9FF0C76B686F7FBB1C4405CED; _n3fa_cid=134b99630baa4a0a94a447be01f08e6c; _n3fa_ext=ft=1528562743; _n3fa_lvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1528562743; _n3fa_lpvt_a9e72dfe4a54a20c3d6e671b3bad01d9=1528562743; uacverifykey=qk08c0fc1280066cb285e8313c16520f06dsju; piw=%7B%22login_name%22%3A%22132****3372%22%2C%22nickName%22%3A%22%E9%BB%84%E7%99%BB%E5%B3%B0%22%2C%22rme%22%3A%7B%22ac%22%3A%22%22%2C%22at%22%3A%22%22%2C%22pt%22%3A%2201%22%2C%22u%22%3A%2213249073372%22%7D%2C%22verifyState%22%3A%22%22%7D; JUT=5K1P+pLlKG5qkApPrcIN3OCdL7c2i1uKf49j9dJpCtA61ldeTkWXphlpyA+CJkXX9htOECk5XEnZGfaDZPpSmJTuHX/PbADlUtaXl8aVuItofUJAME93rlaFAEYNBt2cTReADK+bvXIMn8VHciBTCYfaT9a7r7zXnp3/cdqf3Oufex/fIezFKpxdTDYGeKYdqIOizdG/IuO4IegW5VV5BKTdnjlbNMpBsMcWbgeiSck5ybCAMaeHcmCruOWyYQOgT3s8zOk+522BGgLCDD3lT0AltKKFDpryP/7YibgRsq0sgxRIJFpW5WVx2nQ2qq6687QPvTmsCIquIZDRfeX1g9AxQheX1UZbN6QlRKOCHAcabytzkSJoLYg4IzSfj5JKcBtDaj7P8qmoyynzp3Y3HeYltGkG6IDPs1kE0bqKMm05dGBnbSD/Fuahz0iz6In7UdA/UiRgc1DyxR1ykQAqhVRn/UHesnhkEX4PpkvClt2NiG9/ug58kJ7CD6YpkIiOdH2kqSXF4cBvYxMCZKNBJXjQHwMXaVSpLI31u4f5TMk=RRaC6nBtvOm3o1IYZrgpgg==; _uop_id=b015f7921e758aba47153d979eb576a4");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
	}
	/**
	 * https://upay.10010.com/npfweb/NpfWeb/buyCard/sendPhoneVerifyCode?callback=sendSuccess&commonBean.phoneNo=13249073372&timeStamp=0.474434596328998
	 * @throws ParseException
	 * @throws Exception
	 * sendSuccess('true') 返回格式
	 */
	@Test
	public void chargeSms() throws ParseException, Exception {
		String mobile = "13249073372";
		CookieStore cookieStore = valueOperations.get(mobile);
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookieStore);
		MultiValueMap<String,String> params =  new LinkedMultiValueMap<>();
		params.put("commonBean.phoneNo", Lists.newArrayList(mobile));
		params.put("timeStamp", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));

		String url = UriComponentsBuilder.fromHttpUrl("https://upay.10010.com/npfweb/NpfWeb/buyCard/sendPhoneVerifyCode").queryParams(params).build().toUriString();
		HttpGet request = new HttpGet(url);
		request.setHeader("Referer", "https://uac.10010.com/portal/custLogin");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
	}
	/**
	 * https://upay.10010.com/npfweb/NpfWeb/buyCard/checkPhoneVerifyCode?callback=checkSuccess&commonBean.phoneNo=13249073372&phoneVerifyCode=932453&timeStamp=0.3671002044464746
	 * @throws ParseException
	 * @throws Exception
	 * sendSuccess('true') 返回格式
	 */
	@Test
	public void checkChargeSms() throws ParseException, Exception {
		String mobile = "13249073372";
		CookieStore cookieStore = valueOperations.get(mobile);
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookieStore);
		MultiValueMap<String,String> params =  new LinkedMultiValueMap<>();
		params.put("commonBean.phoneNo", Lists.newArrayList(mobile));
		params.put("phoneVerifyCode", Lists.newArrayList("904114"));
		params.put("timeStamp", Lists.newArrayList(String.valueOf(System.currentTimeMillis())));

		String url = UriComponentsBuilder.fromHttpUrl("https://upay.10010.com/npfweb/NpfWeb/buyCard/checkPhoneVerifyCode").queryParams(params).build().toUriString();
		HttpGet request = new HttpGet(url);
		request.setHeader("Referer", "https://upay.10010.com/npfweb/npfbuycardweb/buycard_recharge_fill.htm");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
	}
	
	
	/**
	 * https://upay.10010.com/npfweb/NpfWeb/buyCard/buyCardCheck?
	 * cardBean.cardValueCode=04&offerPriceStrHidden=100.00
	 * &offerRateStrHidden=1&cardBean.cardValue=100&cardBean.minCardNum=1
	 * &cardBean.maxCardNum=3&MaxThreshold01=15&MinThreshold01=1&MaxThreshold02=10
	 * &MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3
	 * &MinThreshold04=1&commonBean.channelType=101
	 * &secstate.state=3mCBuETgA%2FYTbuZO79gHFA%3D%3D%5E%40%5E0.0.1
	 * &cardBean.buyCardAmount=1&cardBean.buyCardEmail=734839030%40qq.com
	 * &cardBean.buyCardPhoneNo=13249073372&phoneVerifyCode=419906
	 * &invoiceBean.need_invoice=0&invoiceBean.invoice_type=
	 * &invoiceBean.is_mailing=0&saveflag=false&commonBean.provinceCode=&commonBean.cityCode=&invoiceBean.invoice_list=
	 * {"secstate":""}
	 */
	@Test
	public void order() throws ParseException, Exception {
		String mobile = "13249073372";
		String email= "734839030@qq.com";
		String phoneVerifyCode="874501";
		CookieStore cookieStore = valueOperations.get(mobile);
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookieStore);
		String url = UriComponentsBuilder.fromHttpUrl("https://upay.10010.com/npfweb/NpfWeb/buyCard/buyCardCheck").query("cardBean.cardValueCode=04&offerPriceStrHidden=100.00&offerRateStrHidden=1&cardBean.cardValue=100&cardBean.minCardNum=1&cardBean.maxCardNum=3&MaxThreshold01=15&MinThreshold01=1&MaxThreshold02=10&MinThreshold02=1&MaxThreshold03=6&MinThreshold03=1&MaxThreshold04=3&MinThreshold04=1&commonBean.channelType=101&secstate.state=3mCBuETgA%2FYTbuZO79gHFA%3D%3D%5E%40%5E0.0.1&cardBean.buyCardAmount=1&cardBean.buyCardEmail=" + email+ "&cardBean.buyCardPhoneNo=" + mobile+ "&phoneVerifyCode=" + phoneVerifyCode +"&invoiceBean.need_invoice=0&invoiceBean.invoice_type=&invoiceBean.is_mailing=0&saveflag=false&commonBean.provinceCode=&commonBean.cityCode=&invoiceBean.invoice_list=").build().toUriString();
		HttpGet request = new HttpGet(url);
		request.setHeader("Referer", "https://upay.10010.com/npfweb/npfbuycardweb/buycard_recharge_fill.htm");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else {
			throw new ServiceException("请求状态异常失败");
		}
	}
	
	@Test
	public void payPage() throws ParseException, Exception {
		String mobile = "13249073372";
		String email= "734839030@qq.com";
		String phoneVerifyCode="874501";
		CookieStore cookieStore = valueOperations.get(mobile);
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(cookieStore);
		String url = UriComponentsBuilder.fromHttpUrl("https://upay.10010.com/npfweb/NpfWeb/buyCard/buyCardSubmit").build().toUriString();
		HttpPost request = new HttpPost(url);
		Map<String, String> params = new HashMap<String,String>();
		params.put("cardBean.cardValueCode", "04");
		params.put("offerPriceStrHidden", "100.00");
		params.put("offerRateStrHidden", "1");
		params.put("cardBean.cardValue", "100");
		params.put("cardBean.minCardNum", "1");
		params.put("cardBean.maxCardNum", "3");
		params.put("MaxThreshold01", "15");
		params.put("MinThreshold01", "1");
		params.put("MaxThreshold02", "10");
		params.put("MinThreshold02", "1");
		params.put("MaxThreshold03", "6");
		params.put("MinThreshold03", "1");
		params.put("MaxThreshold04", "3");
		params.put("MinThreshold04", "1");
		params.put("commonBean.channelType", "101");
		//params.put("secstate.state", IOUtils.toString(new FileReader("/Users/hdf/Desktop/1.txt")));
		params.put("secstate.state", "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^@^0.0.1");
		params.put("cardBean.buyCardAmount", "1");
		params.put("cardBean.buyCardEmail", email);
		params.put("cardBean.buyCardPhoneNo", mobile);
		params.put("phoneVerifyCode",phoneVerifyCode);
		params.put("invoiceBean.need_invoice", "	0");
		params.put("invoiceBean.invoice_type	", "");
		params.put("invoiceBean.is_mailing", "0");
		params.put("saveflag	", "false");
		params.put("commonBean.provinceCode", "");
		params.put("commonBean.cityCode", "");
		params.put("invoiceBean.invoice_list	", "");
		request.setEntity(getUrlEncodedFormEntity(params));
		request.setHeader("Referer", "https://upay.10010.com/npfweb/npfbuycardweb/buycard_recharge_fill.htm");
		request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
		CloseableHttpResponse response = client.execute(request, httpClientContext);
		System.out.println("response:" + JSON.toJSONString(response));
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {// 成功
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				String result = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				System.out.println("result" + result);
			} else {
				throw new ServiceException("请求无数据返回");
			}
		} else if (response.getStatusLine().getStatusCode() == 302) {
			Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
			String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
			System.out.println("redirect url:" + newuri);
			HttpGet redirectRequest = new HttpGet(newuri);
			CloseableHttpResponse response2 = client.execute(redirectRequest, httpClientContext);
			System.out.println("response2:" + JSON.toJSONString(response2));
		} else {
			throw new ServiceException("请求状态异常失败");
		}
	}
	private UrlEncodedFormEntity getUrlEncodedFormEntity(Map<String, String> params) {
		UrlEncodedFormEntity entity = null;
		if (null != params && !params.isEmpty()) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				entity = new UrlEncodedFormEntity(list, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return entity;
	}
	
	@Test
	public void testBuilder() {
		MultiValueMap<String,String> params =  new LinkedMultiValueMap<>();
		params.put("dsa", Lists.newArrayList("dsa"));
		params.put("ds111a", Lists.newArrayList("11dsa"));
		UriComponents build = UriComponentsBuilder.fromHttpUrl("https://uac.10010.com/portal/Service/SendMSG?d=2").queryParams(params).build();
		System.out.println(build.toUriString());
	}
	@Test
	public void testDate() {
		System.out.println(new Date().getTime());
		System.out.println(System.currentTimeMillis());
	}
}
