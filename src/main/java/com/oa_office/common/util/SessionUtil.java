package com.oa_office.common.util;

import javax.servlet.http.HttpSession;

import com.oa_office.user.pojo.User;

public class SessionUtil {
	public static final String USER = "user";
	public static final String USERID = "userId";

	/**
	 * 设置用户到session
	 */
	public static void setUser(HttpSession session, User user) {
		session.setAttribute(USER, user);
		setUserId(session, user.getId());
	}

	/**
	 * 从Session获取当前用户信息
	 */
	public static User getUser(HttpSession session) {
		Object user = session.getAttribute(USER);
		return user == null ? null : (User) user;
	}

	/**
	 * 设置用户到session
	 */
	public static void setUserId(HttpSession session, String userId) {
		session.setAttribute(USERID, userId);
	}

	/**
	 * 从Session获取当前用户信息
	 */
	public static String getUserId(HttpSession session) {
		Object userId = session.getAttribute(USERID);
		return userId == null ? null : (String) userId;
	}

	public static void removeAttribute(HttpSession session) {
		session.removeAttribute(USER);
		session.removeAttribute(USERID);
	}

}
