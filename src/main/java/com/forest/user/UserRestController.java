package com.forest.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forest.common.EncryptUtils;
import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RestController
public class UserRestController {
	
	private final UserBO userBO;
	
	public UserRestController(UserBO userBO) {
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
	@PostMapping("/auth/sign-in")
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
			result.put("error_message", 401);
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
	@PostMapping("/auth/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email) throws NoSuchAlgorithmException {
		
		String hashedPassword = EncryptUtils.sha256(password);
		
		userBO.addUser(loginId, hashedPassword, name, phoneNumber, email);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 회원가입 API
	
	// 회원정보수정 API
	@PutMapping("/user/update")
	public Map<String, Object> update(
			@RequestParam("currentPassword") String currentPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("name") String name,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("address") String address,
			HttpSession session) throws NoSuchAlgorithmException {

		// 현재 session 정보
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// 현재 비밀번호 일치 확인
		String hashedPassword = EncryptUtils.sha256(currentPassword);
		User user = userBO.getUserByLoginIdAndPassword(userLoginId, hashedPassword);
		
		Map<String, Object> result = new HashMap<>();
		if (user == null) {
			result.put("code", 401);
			result.put("error_message", "비밀번호가 일치하지 않습니다.");
			return result;
		}
		
		// 업데이트
		
		
		// 리턴
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
