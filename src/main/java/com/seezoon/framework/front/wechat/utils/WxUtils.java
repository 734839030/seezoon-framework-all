package com.seezoon.framework.front.wechat.utils;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.RandomStringUtils;

import com.seezoon.framework.common.utils.CodecUtils;

public class WxUtils {

	/**
	 * 随机串
	 * @return
	 */
	public static String createNoncestr() {
		return RandomStringUtils.randomAlphanumeric(16);
	}
	/**
	 * 时间戳
	 * 
	 * @return
	 */
	public static long createTimestamp() {
		return System.currentTimeMillis();
	}
	
	public static String jsSignature(String noncestr,long timestamp, String jsapiTicket,String url) {
		TreeMap<String,Object> treeMap = new TreeMap<>();
		treeMap.put("noncestr", noncestr);
		treeMap.put("jsapi_ticket", jsapiTicket);
		treeMap.put("timestamp", timestamp);
		treeMap.put("url", url);
		String sortStr = createSortStr(treeMap);
		return CodecUtils.sha1(sortStr);
	}
	/**
	 * 生成排序字符串
	 * @param map
	 * @return
	 */
	public static String createSortStr(TreeMap<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}
	public static void main(String[] args) {
		System.out.println(createNoncestr());
		System.out.println(createTimestamp());
		System.out.println(new Date().getTime());
	}
}
