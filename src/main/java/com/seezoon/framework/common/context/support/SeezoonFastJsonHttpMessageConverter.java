package com.seezoon.framework.common.context.support;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.seezoon.framework.common.context.beans.ResponeModel;

/**
 * 实现国际化，信息标准转化输出
 * 
 * @author hdf 2018年3月31日
 */
public class SeezoonFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

	/**
	 * 消息资源文件
	 */
	@Autowired
	private MessageSource messageSource;

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (null != obj && obj instanceof ResponeModel) {
			ResponeModel responeModel = (ResponeModel) obj;
			String responeCode = responeModel.getResponeCode();
			String msg = messageSource.getMessage(responeCode, responeModel.getParams(), responeModel.getResponeMsg(),
					this.getLocale());
			responeModel.setResponeMsg(msg);
		}
		super.writeInternal(obj, outputMessage);
	}

	/**
	 * 如果要实现国际化，改变这个方法的返回值即可，可以通过Filter设置ThreadLocal传入国际化参数
	 * 
	 * @return
	 */
	protected Locale getLocale() {
		return Locale.CHINA;
	}
}
