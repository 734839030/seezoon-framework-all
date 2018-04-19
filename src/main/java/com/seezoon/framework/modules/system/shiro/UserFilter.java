package com.seezoon.framework.modules.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 账密或者remember 的拦截器 
 * 
 * 解决在remember的情况下，跨域请求options 请求无法携带cookie 的问题
 *   
 * @author hdf
 * 2018年4月19日
 */
@Component
public class UserFilter extends org.apache.shiro.web.filter.authc.UserFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest)request;
		//跨域的options 请求直接过
		if (RequestMethod.OPTIONS.name().equalsIgnoreCase(req.getMethod())) {
			return true;
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return super.isAccessAllowed(request, response, mappedValue);
	}
}
