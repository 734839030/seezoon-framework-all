package com.seezoon.framework.common.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.aliyun.oss.ServiceException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 验证码生成类
 * 
 * @author hdf 2018年4月24日
 */
public class KaptchaHelper {

	/**
	 * 是否有边框 可选yes 或者 no
	 */
	private String border = "no";
	/**
	 * 边框颜色 三位逗号分隔
	 */
	private String borderColor = "105,179,90";
	/**
	 * 验证码文本字符颜色
	 */
	private String fontColor = "blue";

	/**
	 * 验证码文本字符大小
	 */
	private String fontSize = "45";

	/**
	 * 验证码图片的宽度 默认200
	 */
	private String imageWidth = "200";
	/**
	 * 验证码图片的高度 默认50
	 */
	private String imageHeight = "45";

	/**
	 * 验证码文本字符长度
	 */
	private String charLength = "4";
	/**
	 * 验证码文本字体样式
	 */
	private String fontNames = "宋体,楷体,微软雅黑";

	DefaultKaptcha defaultKaptcha;

	public KaptchaHelper() {
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", border);
		properties.setProperty("kaptcha.border.color", borderColor);
		properties.setProperty("kaptcha.textproducer.font.color", fontColor);
		properties.setProperty("kaptcha.textproducer.font.size", fontSize);
		properties.setProperty("kaptcha.image.width", imageWidth);
		properties.setProperty("kaptcha.image.height", imageHeight);
		properties.setProperty("kaptcha.textproducer.char.length", charLength);
		properties.setProperty("kaptcha.textproducer.font.names", fontNames);
		//间隔
		properties.setProperty("kaptcha.textproducer.char.space", "3");
		
//		图片样式：
//		水纹com.google.code.kaptcha.impl.WaterRipple
//		鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
//		阴影com.google.code.kaptcha.impl.ShadowGimpy
		properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.FishEyeGimpy");
		
		Config config = new Config(properties);
		defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
	}

	/**
	 *  使用的时候注意加上请求头，防止缓存
	 * response.setDateHeader("Expires", 0);  
         response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
         response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
         response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");
	 * @param out
	 * @return
	 */
	public String createImage(OutputStream out) {
		String text = defaultKaptcha.createText();
		BufferedImage image = defaultKaptcha.createImage(text);
		try {
			ImageIO.write(image, "jpg", out);
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		return text;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	public String getCharLength() {
		return charLength;
	}

	public void setCharLength(String charLength) {
		this.charLength = charLength;
	}

	public String getFontNames() {
		return fontNames;
	}

	public void setFontNames(String fontNames) {
		this.fontNames = fontNames;
	}

}
