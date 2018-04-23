package com.seezoon.framework.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.context.exception.ServiceException;

/**
 * 对性能和参数要求敏感，需要自行利用 HttpPoolClient 对象自行构造
 * 
 * @author hdf 2018年4月23日
 */
public class HttpRequestUtils {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

	private static String DEFAULT_CHARSET = "UTF-8";

	private static HttpPoolClient defaultHttpPoolClient = new HttpPoolClient();

	public static <T> T doGet(String url, Map<String, String> params, Class<T> clazz) {
		return JSON.parseObject(doGet(url, params), clazz);
	}

	public static <T> T doPost(String url, Map<String, String> params, Class<T> clazz) {
		return JSON.parseObject(doPost(url, params), clazz);
	}

	public static <T> T postJson(String url, Map<String, String> params, Class<T> clazz) {
		return JSON.parseObject(postJson(url, params), clazz);
	}

	public static String postJson(String url, Map<String, String> params) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(JSON.toJSONString(params), ContentType.APPLICATION_JSON));
		return execute(httpPost);
	}

	public static String doGet(String url, Map<String, String> params) {
		Assert.hasLength(url, "请求地址为空");
		HttpGet httpGet = new HttpGet(url);
		UrlEncodedFormEntity urlEncodedFormEntity = getUrlEncodedFormEntity(params);
		if (urlEncodedFormEntity != null) {
			try {
				String paramStr = EntityUtils.toString(urlEncodedFormEntity);
				URL urlO = new URL(url);
				String query = urlO.getQuery();
				if (StringUtils.isNotEmpty(query)) {
					httpGet.setURI(URI.create(url + "&" + paramStr));
				} else {
					httpGet.setURI(URI.create(url + "?" + paramStr));
				}
			} catch (Exception e) {
				throw new ServiceException(e);
			}
		}
		String result = execute(httpGet);
		return result;
	}

	public static String doPost(String url, Map<String, String> params) {
		logger.debug("test log");
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(getUrlEncodedFormEntity(params));
		return execute(httpPost);
	}

	public static String execute(HttpRequestBase request) {
		CloseableHttpResponse response = null;
		try {
			response = defaultHttpPoolClient.execute(request);
			int status = response.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK == status) {// 成功
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
					EntityUtils.consume(entity);
					return result;
				} else {
					throw new ServiceException("请求无数据返回");
				}
			} else {
				throw new ServiceException("请求状态异常失败");
			}
		} catch (Exception e) {
			throw new ServiceException(request.getURI().toString() + "请求失败", e);
		} finally {
			if (null != response) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("CloseableHttpResponse close error", e);
				}
			}
		}
	}

	private static UrlEncodedFormEntity getUrlEncodedFormEntity(Map<String, String> params) {
		UrlEncodedFormEntity entity = null;
		if (null != params && !params.isEmpty()) {
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				entity = new UrlEncodedFormEntity(list, DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException e) {
			}
		}
		return entity;
	}

	public static void shutDown() {
		defaultHttpPoolClient.shutdown();
	}
}
