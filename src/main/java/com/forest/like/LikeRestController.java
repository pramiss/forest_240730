package com.forest.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.forest.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	private final LikeBO likeBO;
	
	public LikeRestController(LikeBO likeBO) {
		this.likeBO = likeBO;
	}

	/**
	 * like 토글 API
	 * @param isbn
	 * @param session
	 * @return
	 */
	@GetMapping("/like/{isbn}")
	public Map<String, Object> likeToggle(
			@PathVariable(name = "isbn") String isbn,
			HttpSession session) {
		
		// 1. 로그인 검사
		Integer userId = (Integer)session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();
		
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 필요합니다.");
			return result;
		}
		
		// 2. BO에서 좋아요 토글
		likeBO.likeToggle(userId, isbn);
		
		// 3. 결과 리턴
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 좋아요 토글
}
