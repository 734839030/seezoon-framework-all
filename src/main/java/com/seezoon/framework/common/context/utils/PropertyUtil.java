package com.seezoon.framework.common.context.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 配置文件读取
 * 
 * @author hdf 2018年4月19日
 */
public class PropertyUtil {

	private static Configuration config = null;

	static {
		try {
			// 默认从classpath 根路径开始
			config = new PropertiesConfiguration("application.properties");
		} catch (ConfigurationException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean getBoolean(String key) {
		return config.getBoolean(key, false);
	}

	public static int getInteger(String key) {
		return config.getInteger(key, null);
	}

	public static String getString(String key) {
		return config.getString(key, null);
	}
}
