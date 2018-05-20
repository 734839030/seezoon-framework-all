package com.seezoon.framework.modules.system.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.seezoon.framework.common.entity.AdminUser;
import com.seezoon.framework.common.utils.CurrentThreadContext;
import com.seezoon.framework.modules.system.shiro.ShiroUtils;
import com.seezoon.framework.modules.system.shiro.User;

/**
 * 后端通用拦截器
 * 
 * @author hdf 2017年9月24日
 */
public class SystemFilter implements HandlerInterceptor {
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(SystemFilter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = ShiroUtils.getUser();
		if (null != user) {
			CurrentThreadContext.putUser(new AdminUser(user.getUserId(), user.getDeptId()));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		CurrentThreadContext.removeUser();
	}
}
