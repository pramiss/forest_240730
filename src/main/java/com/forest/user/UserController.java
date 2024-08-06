package com.forest.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	private final UserBO userBO;
	
	public UserController(UserBO userBO) {
	    this.userBO = userBO;
	} 
	
	
	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/auth/login")
	public String userLogin(HttpSession session) {
		return "user/login";
	} //-- 로그인 페이지
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/auth/join")
	public String userJoin(HttpSession session) {
		return "user/join";
	} //-- 회원가입 페이지
	
	@GetMapping("/user/info")
	public String userInfo(HttpSession session, Model model) {
		Integer userId = (Integer)session.getAttribute("userId");

		// 유저 아이디로 user를 가져온 후 model에 담기
		User user = userBO.getUserById(userId);
		model.addAttribute("user", user);
		
		return "user/info";
	} //-- 회원상세 페이지
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	@GetMapping("/user/sign-out")
	public String signOut(HttpSession session) {
		
		// 로그아웃
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// 리다이렉트
		return "redirect:/";
	} //-- 로그아웃API
}
