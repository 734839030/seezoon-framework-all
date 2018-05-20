package com.seezoon.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seezoon.framework.common.entity.AdminUser;

public class CurrentThreadContext {

	/**
	 * 利用了容器的线程池，ThreadLocal 数据会相互干扰
	 */
	private static ThreadLocal<AdminUser> threadLocal = new ThreadLocal<>();
	protected static Logger logger = LoggerFactory.getLogger(CurrentThreadContext.class);
	public static void putUser(AdminUser adminUser) {
		threadLocal.remove();
		threadLocal.set(adminUser);
	}

	public static AdminUser getUser() {
		 AdminUser adminUser =  threadLocal.get();
		return adminUser;
	}
	public static void removeUser() {
		threadLocal.remove();
	}
}
