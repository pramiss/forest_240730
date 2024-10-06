package com.forest.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.user.bo.UserBO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public String authLogin(HttpSession session, Model model,
		    @Value("${kakao.client_id}") String client_id,
		    @Value("${kakao.redirect_uri}") String redirect_uri) {
		
		log.info("***** [Into Login Page]");
		
		String kakaoLogin = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
		model.addAttribute("kakaoLogin", kakaoLogin);
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
	
	/**
	 * 카카오 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join/kakao")
	public String authJoinKakao(HttpSession session,
			Model model,
			@RequestParam("kakaoId") String kakaoId,
			@RequestParam("nickname") String nickname) {
		
		model.addAttribute("kakaoId", kakaoId);
		model.addAttribute("nickname", nickname);
		
		return "auth/joinKakao";
	} //-- 회원가입 페이지
}
