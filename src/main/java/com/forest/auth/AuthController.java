package com.forest.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")
@Controller
public class AuthController {

	private final UserBO userBO;
	
	public AuthController(UserBO userBO) {
	    this.userBO = userBO;
	}
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public String authLogin(HttpSession session) {
		return "auth/login";
	} //-- 로그인 페이지
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String authJoin(HttpSession session) {
		return "auth/join";
	} //-- 회원가입 페이지
}
