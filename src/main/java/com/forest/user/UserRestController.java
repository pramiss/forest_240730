package com.forest.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forest.common.EncryptUtils;
import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	private final UserBO userBO;
	
	public UserRestController(UserBO userBO) {
	    this.userBO = userBO;
	}
	
	
	
	/**
	 * 회원정보수정 API
	 * @param currentPassword
	 * @param newPassword
	 * @param name
	 * @param phoneNumber
	 * @param email
	 * @param address
	 * @param session
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping("/update")
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
		if (!newPassword.isEmpty()) {
			hashedPassword = EncryptUtils.sha256(newPassword);			
		}
		userBO.updateUserById(userId, hashedPassword, name, phoneNumber, email, address);
		
		// 리턴
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 회원정보수정API
}
