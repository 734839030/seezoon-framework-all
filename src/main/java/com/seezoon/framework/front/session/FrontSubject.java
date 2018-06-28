package com.seezoon.framework.front.session;

import javax.servlet.http.HttpSession;

/**
 * 前端登录态
 * @author hdf
 * 2018年6月27日
 */
public class FrontSubject {

	private static ThreadLocal<FrontUser> userContext = new ThreadLocal<>();
	private static String FRONT_SESSION_KEY = "front_session_key";
	public static void put(FrontUser frontUser) {
		userContext.remove();
		userContext.set(frontUser);
	}
	public static FrontUser get() {
		return userContext.get();
	}
	public static void putUserSession(HttpSession session,FrontUser frontUser) {
		session.setAttribute(FRONT_SESSION_KEY,frontUser);
	}
	public static FrontUser getUserSession(HttpSession session) {
		return (FrontUser)session.getAttribute(FRONT_SESSION_KEY);
	}
}
