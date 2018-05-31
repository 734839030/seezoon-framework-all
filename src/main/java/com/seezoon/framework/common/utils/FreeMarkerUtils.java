package com.seezoon.framework.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSON;
import com.seezoon.framework.common.context.exception.ServiceException;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtils {

	public static final String DEFAULT_DIRECTORY = "template";
	private static Configuration cfg = buildConfiguration(DEFAULT_DIRECTORY);

	public static Configuration buildConfiguration(String directory) {
		// 1.创建配置实例Cofiguration
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setDefaultEncoding("utf-8");
		ClassPathResource cps = new ClassPathResource(directory);
		try {
			cfg.setDirectoryForTemplateLoading(cps.getFile());
			return cfg;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 
	 * @param name
	 *            相对路径下的模板
	 * @param data
	 *            数据
	 * @param out
	 *            输出流
	 */
	public static void renderTemplate(String name, Object data, Writer out) {
		try {
			Template template = cfg.getTemplate(name);
			template.process(data, out);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 获取渲染后的文本
	 * @param name
	 *            相对路径下的模板
	 * @param data
	 *            数据
	 * @param out
	 *            输出流
	 */
	public static String renderTemplate(String name, Object data) {
		String result = null;
		try {
			Template template = cfg.getTemplate(name);
			StringWriter out = new StringWriter();
			template.process(data, out);
			result = out.toString();
			out.close();
			return result;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	/**
	 * 模板内容
	 * @param name
	 *            相对路径下的模板
	 */
	public static String readTemplate(String name) {
		try {
			Template template = cfg.getTemplate(name);
			return template.toString();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 字符串模板
	 * @param templateString
	 * @param data
	 * @return
	 */
	public static String renderStringTemplate(String templateString, Object data) {
		Template template;
		String result = null;
		try {
			template = new Template(null, new StringReader(templateString), cfg);
			StringWriter sw = new StringWriter();
			template.process(data, sw);
			result = sw.toString();
			sw.close();
		} catch (Exception e) {
			throw new ServiceException(e);
		}

		return result;
	}
}
