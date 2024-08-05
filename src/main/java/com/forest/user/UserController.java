package com.forest.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
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
	@GetMapping("/login")
	public String userLogin(HttpSession session) {
		
		// 이미 로그인 된 경우 홈으로.
		if (session.getAttribute("userId") != null) {
			return "redirect:/";
		}
		
		return "user/login";
	} //-- 로그인 페이지
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String userJoin(HttpSession session) {

		// 이미 로그인 된 경우 홈으로.
		if (session.getAttribute("userId") != null) {
			return "redirect:/";
		}
		
		return "user/join";
	} //-- 회원가입 페이지
	
	@GetMapping("/info")
	public String userInfo(HttpSession session, Model model) {
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 로그인 안된 경우 로그인 페이지로.
		if (userId == null) {
			return "redirect:/user/login";
		}
		
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
