package com.seezoon.framework.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编解码器
 * 
 * @author hdf 2018年4月16日
 */
public class CodecUtils {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * url编码
	 * 
	 * @param text
	 * @return
	 */
	public static String urlEncode(String text) {
		try {
			return URLEncoder.encode(text, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {

		}
		return null;
	}

	/**
	 * url解码
	 * 
	 * @param text
	 * @return
	 */
	public static String urlDecode(String text) {
		try {
			return URLDecoder.decode(text, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {

		}
		return null;
	}
}
