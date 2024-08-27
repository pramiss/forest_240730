package com.forest.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

	// 결제 API
	@PostMapping("/order/confirmation")
	public Map<String, Object> confirmation(
			@RequestParam("userId") int userId,
			@RequestParam("totalPrice") int totalPrice,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("productIdList") List<Integer> productIdList) {
		
		// TODO: 결제 API 요청
		
		// 1) 결제 실패 로직
		
		// 2) 결제 성공 로직
		
		// 1. order DB에 저장
		
		// 2. orderProduct DB에 저장
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
