package com.forest.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	} //-- 로그인 페이지
	
	@GetMapping("/join")
	public String userJoin() {
		return "user/join";
	} //-- 회원가입 페이지
}
