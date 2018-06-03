package com.seezoon.framework.common.context.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.seezoon.framework.common.context.utils.NDCUtils;

public class TraceFilter implements Filter {

	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(TraceFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		NDCUtils.push();
		StopWatch watch = new StopWatch();
		watch.start();
		chain.doFilter(request, response);
		watch.stop();
		String requestURI = httpServletRequest.getRequestURI();
		logger.info("request:{} comleted use {} ms",requestURI,watch.getTotalTimeMillis());
		NDCUtils.clear();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
		
	}

}
