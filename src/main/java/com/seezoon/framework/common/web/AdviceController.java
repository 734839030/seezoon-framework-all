package com.seezoon.framework.common.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.seezoon.framework.common.context.beans.ResponeModel;
import com.seezoon.framework.common.context.exception.ExceptionCode;
import com.seezoon.framework.common.context.exception.ResponeException;
import com.seezoon.framework.common.utils.DateUtils;

/**
 * controller 额外逻辑处理类 常用作异常处理
 * 
 * @author hdf 2017年12月11日
 */
@Component
@ControllerAdvice
public class AdviceController {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(AdviceController.class);
	@Value("${web.maxUploadSize}")
	private Long maxUploadSize;
	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}

	@ResponseBody
	@ExceptionHandler(ResponeException.class)
	public ResponeModel responeException(ResponeException e) {
		logger.error("respone exception ", e);
		ResponeModel responeModel = ResponeModel.error(e.getResponeCode(), e.getResponeMsg());
		responeModel.setParams(e.getParams());
		return responeModel;
	}

	@ResponseBody
	@ExceptionHandler(BindException.class)
	public ResponeModel bindException(BindException e) {
		logger.error("bind exception ", e);
		ResponeModel responeModel = ResponeModel.error(ExceptionCode.PARAM_BIND_ERROR, "参数绑定错误");
		responeModel.setParams(new Object[] { e.getMessage() });
		return responeModel;
	}
	
	/**
	 * @throws IOException  测试无法拦截
	 */
	@ResponseBody
	@ExceptionHandler({MaxUploadSizeExceededException.class,SizeLimitExceededException.class})
	public ResponeModel maxUploadSizeExceededExceptionHandler() {
		return ResponeModel.error("文件超过" + maxUploadSize/1024 + "KB");
	}
	/**
	 * 未授权,权限不足
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@ExceptionHandler(UnauthorizedException.class)
	public ResponeModel exceptionHandler(HttpServletResponse response) {
		response.setStatus(HttpStatus.NEED_PERMISSION.getValue());
		return ResponeModel.error(ExceptionCode.PERMISSION_DENIED, "权限不足，请联系管理员");
	}
	/**
	 * 可以细化异常，spring 从小异常抓，抓到就不往后走
	 * 
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponeModel exception(Exception e) {
		logger.error("global exception ", e);
		ResponeModel responeModel = ResponeModel.error(ExceptionCode.UNKNOWN, e.getMessage());
		responeModel.setParams(new Object[] { e.getMessage() });
		return responeModel;
	}
}
