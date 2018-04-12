package com.oa_office.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter {

	private Map<String, String> menus;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = request.getRequestURI();

		if (uri.contains(".css") || uri.contains(".js") || uri.contains(".ico")||uri.contains(".png") || uri.contains(".jpg")
				|| uri.contains(".html")) {
			chain.doFilter(request, response);
		} else {

			// 判断当前请求地址是否是登录的请求地址
			if (!uri.contains("/login")) {
				// 非登录请求
				if (SessionUtil.getUser(request.getSession()) != null) {	
						// 说明已经登录过,直接放行
						chain.doFilter(request, response);	
				} else {
					// 没有登录，跳转到登录页面
					response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
			} else {
				// 登录请求；直接放行
				chain.doFilter(request, response);
				// response.sendRedirect(request.getContextPath() + "/login.jsp");
			}

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		menus = new HashMap<String, String>();
	}

}
