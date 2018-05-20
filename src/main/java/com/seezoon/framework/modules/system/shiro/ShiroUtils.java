package com.seezoon.framework.modules.system.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.seezoon.framework.common.Constants;

/**
 * Shiro工具类
 * 
 * @author hdf
 *
 */
public class ShiroUtils {
	/** 加密算法 */
	public final static String hashAlgorithmName = "SHA-256";
	/** 循环次数 */
	public final static int hashIterations = 16;

	public static String sha256(String password, String salt) {
		return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static User getUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	public static String getUserId() {
		return getUser().getUserId();
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		//System.out.println("isAuthenticated:" + getSubject().isAuthenticated()) ;
		//System.out.println("isRemembered:" + getSubject().isRemembered()) ;
		return  SecurityUtils.getSubject().isAuthenticated() && SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	public static boolean isSuperAdmin() {
		return isSuperAdmin(getUserId());
	}
	public static boolean isSuperAdmin(String userId) {
		return Constants.SUPER_ADMIN_ID.equals(userId);
	}

}
