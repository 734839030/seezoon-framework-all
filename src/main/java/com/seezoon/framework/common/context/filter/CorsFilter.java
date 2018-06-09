package com.seezoon.framework.common.context.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seezoon.framework.common.context.utils.PropertyUtil;

/**
 * 测试时候前后端分离 在web.xml 配置
 * 
 * @author hdf 2018年4月7日
 */
public class CorsFilter implements Filter {

	protected Logger logger = LoggerFactory.getLogger(CorsFilter.class);
	private boolean cors = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		cors = PropertyUtil.getBoolean("cors.switch");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (cors) {
			logger.warn("-------- cors is turn on ----------");
			HttpServletResponse servletResponse = (HttpServletResponse) response;
			HttpServletRequest servletRequest = (HttpServletRequest) request;
			String origin = servletRequest.getHeader("Origin");
			if (StringUtils.isNotEmpty(origin)) {
				//允许客户端携带跨域cookie，此时origin值不能为“*”，只能为指定单一域名
				servletResponse.setHeader("Access-Control-Allow-Origin", origin);
			} else {
				servletResponse.setHeader("Access-Control-Allow-Origin", "*");
			}
			servletResponse.setHeader("Access-Control-Allow-Methods", "*");
			servletResponse.setHeader("Access-Control-Allow-Headers", "*,Content-Type");
			servletResponse.setHeader("Access-Control-Expose-Headers", "*");
			servletResponse.setHeader("Access-Control-Max-Age", "3600");
			servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
