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
	 * 회원정보상세 API
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/info")
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
