package com.forest.auth;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forest.common.EncryptUtils;
import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/auth")
@RestController
public class AuthRestController {

	private final UserBO userBO;
	
	public AuthRestController(UserBO userBO) {
	    this.userBO = userBO;
	}
	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session) throws NoSuchAlgorithmException {
		
		String hashedPassword = EncryptUtils.sha256(password);
		
		// 1. loginId, hashedPassword 에 해당하는 계정 가져오기
		Map<String, Object> result = new HashMap<>();
		User user = userBO.getUserByLoginIdAndPassword(loginId, hashedPassword);
		
		// 2. 없으면 401 back.
		if (user == null) {
			result.put("code", 401);
			result.put("error_message", "로그인 실패");
			return result;
		}
		
		// 3. 그런 유저가 존재하면 그 유저 세션에 담기
		session.setAttribute("userId", user.getId());
		session.setAttribute("userLoginId", user.getLoginId());
		session.setAttribute("userName", user.getName());
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 로그인 API
	
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam(value = "email", required = false) String email) throws NoSuchAlgorithmException {
		
		String hashedPassword = EncryptUtils.sha256(password);
		
		userBO.addUser(loginId, hashedPassword, name, phoneNumber, email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 회원가입 API
	
	// 아이디 중복확인 API
	@GetMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(@RequestParam("loginId") String loginId) {
		
		// 중복확인
		User user = userBO.getUserByLoginId(loginId);
		
		Map<String, Object> result = new HashMap<>();

		if (user != null) { // 중복인 경우
			result.put("code", 202);
			result.put("result", "중복");
			return result;
		}
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 아이디 중복확인 API
}
