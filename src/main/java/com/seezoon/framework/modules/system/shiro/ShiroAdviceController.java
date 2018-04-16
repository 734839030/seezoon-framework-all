package com.seezoon.framework.modules.system.shiro;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seezoon.framework.common.context.beans.ResponeModel;

@ControllerAdvice
public class ShiroAdviceController {
	/**
	 * 未授权,权限不足
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@ExceptionHandler(UnauthorizedException.class)
	public ResponeModel exceptionHandler(HttpServletResponse response) {
		response.setStatus(311);
		return ResponeModel.error("90004", "未登录");
	}
}
