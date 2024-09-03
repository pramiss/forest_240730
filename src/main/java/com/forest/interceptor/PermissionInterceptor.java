package com.forest.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

	// PreHandler
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws IOException {
		
		// 현재 요청 주소 확인
		String uri = request.getRequestURI();
		log.info("[@@@@@ PermissionInterceptor preHandle, uri:{}]", uri);
		
		// 로그인 상태 확인
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		
		// 로그인 상태 && /auth -> 메인페이지로.
		if (userId != null && uri.startsWith("/auth")) {
			response.sendRedirect("/");
			return false; // 원래 Controller 기능은 수행하지 않음
		}
		
		// 로그아웃 상태 && /user, /order -> 로그인 페이지로.
		if (userId == null) {
			if (uri.startsWith("/user") || uri.startsWith("/order")) {
				response.sendRedirect("/auth/login");
				return false;
			}
		}
		
		// 관리자(admin) 페이지 (관리자 아이디 번호 : 0)
		if (uri.startsWith("/admin") && (userId == null || userId != 0)) {
			response.sendRedirect("/");
			return false;
		}
		
		
		// 나머지
		return true;
	}
	
	// PostHandler - HTML 해석 전
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) {
		String uri = request.getRequestURI();
		log.info("[$$$$$ PermissionInterceptor postHandle, uri:{}]", uri);
	}
	
	
	// AfterCompletion - - HTML 해석 후
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) {
		String uri = request.getRequestURI();
		log.info("[##### PermissionInterceptor afterCompletion, uri:{}]", uri);
	}
}
