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

public class CorsFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		String origin = servletRequest.getHeader("Origin");
		if (StringUtils.isNotEmpty(origin)) {
			servletResponse.setHeader("Access-Control-Allow-Origin", origin);
		} else {
			servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		}
		servletResponse.setHeader("Access-Control-Allow-Methods", "*");
		servletResponse.setHeader("Access-Control-Allow-Headers", "*,Content-Type");
		servletResponse.setHeader("Access-Control-Expose-Headers", "*");
		servletResponse.setHeader("Access-Control-Max-Age", "3600");
		servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(request, servletResponse);
	}

	@Override
	public void destroy() {

	}

}
