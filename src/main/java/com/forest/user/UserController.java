package com.forest.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	} //-- 로그인 페이지
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String userJoin() {
		return "user/join";
	} //-- 회원가입 페이지
	
	@GetMapping("/info")
	public String userInfo(HttpSession session, Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			return "/";
		}
		
		return "user/info";
	} //-- 회원상세 페이지
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		// 로그아웃
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// 리다이렉트
		return "redirect:/";
	} //-- 로그아웃API
}
