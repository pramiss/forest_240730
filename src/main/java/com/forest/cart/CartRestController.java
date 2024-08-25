package com.forest.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forest.cart.bo.CartBO;

import jakarta.servlet.http.HttpSession;

@RestController
public class CartRestController {

	private final CartBO cartBO;
	
	public CartRestController (CartBO cartBO) {
		this.cartBO = cartBO;
	}
	
	/**
	 * 장바구니 추가 API
	 * @param productId
	 * @param session
	 * @return
	 */
	@PostMapping("/cart/{productId}")
	public Map<String, Object> addCart(
			@PathVariable(name = "productId") int productId,
			HttpSession session) {
		
		// 로그인 검사
		Integer userId = (Integer)session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();
		
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 필요합니다.");
			return result;
		}
		
		// 이미 장바구니에 있는지 확인
		if (cartBO.getCartById(userId, productId) != null) {
			result.put("code", 501);
			result.put("error_message", "이미 장바구니에 있습니다.");
			return result;
		}
		
		// 장바구니에 (userId, productId) 추가
		if (cartBO.addCart(userId, productId) == false) {
			result.put("code", 500);
			result.put("error_message", "구매가 불가능한 상품입니다.");
			return result;
		}
		
		// return
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 장바구니 추가 API
	
	// 장바구니 삭제 API (리스트로 한꺼번에 삭제)
	@DeleteMapping("/cart/{productIdList}")
	public Map<String, Object> deleteCartList(
			@PathVariable(name = "productIdList") List<Integer> productIdList,
			HttpSession session) {
		
		// 로그인 검사
		Integer userId = (Integer)session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();
		
		if (userId == null) {
			result.put("code", 403);
			result.put("error_message", "로그인이 필요합니다.");
			return result;
		}

		// cartBO를 통해 해당하는 productId 목록 삭제
		cartBO.deleteCartList(userId, productIdList);
		
		// 성공 후 리턴
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	} //-- 장바구니 삭제 API
}
