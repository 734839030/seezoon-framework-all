package com.seezoon.framework.common.context.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.seezoon.framework.common.context.utils.NDCUtils;

/**
 * 线程上下文及耗时日志
 * @author hdf
 * 2017年9月24日
 */
public class TraceWebFilter implements HandlerInterceptor {
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(TraceWebFilter.class);
	ThreadLocal<StopWatch> threadLocal = new ThreadLocal<>();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		NDCUtils.push();
		StopWatch watch = new StopWatch();
		watch.start();
		threadLocal.set(watch);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		StopWatch stopWatch = threadLocal.get();
		stopWatch.stop();
		String requestURI = request.getRequestURI();
		logger.info("request:{} comleted use {} ms",requestURI,stopWatch.getTotalTimeMillis());
		threadLocal.remove();
		NDCUtils.clear();
	}
}
