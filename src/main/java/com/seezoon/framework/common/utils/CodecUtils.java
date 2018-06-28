package com.seezoon.framework.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;

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
	
	public static byte[] base64Decode(String data) {
		return Base64.decodeBase64(data);
	}
	
	public static String base64Encode(byte[] data) {
		return Base64.encodeBase64String(data);
	}
	public static String md5(String data) {
		Assert.hasLength(data,"待md5 数据为空");
		return DigestUtils.md5Hex(data);
	}
	public static String sha1(String data) {
		Assert.hasLength(data,"待sha1 数据为空");
		return DigestUtils.sha1Hex(data);
	}
	
	public static String sha256(String data) {
		Assert.hasLength(data,"待sha256 数据为空");
		return DigestUtils.sha256Hex(data);
	}
	public static void main(String[] args) {
		
	}
}
