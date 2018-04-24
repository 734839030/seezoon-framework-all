package com.seezoon.framework.common.context.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.NDC;

/**
 * log ndc
 * 
 * @author hdf 2018年3月31日
 */
public class NDCUtils {

	public static void push() {
		clear();
		NDC.push(randomThreadId());
	}

	public static String peek() {
		String peek = NDC.peek();
		return StringUtils.isNotEmpty(peek) ? peek : null;
	}

	public static void clear() {
		NDC.clear();
	}

	private static String randomThreadId() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
