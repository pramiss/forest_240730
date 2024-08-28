package com.forest.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.forest.order.bo.OrderBO;
import com.forest.order.bo.OrderProductBO;

@RestController
public class OrderRestController {

	private final OrderBO orderBO;
	private final OrderProductBO orderProductBO;
	
	public OrderRestController (OrderBO orderBO, OrderProductBO orderProductBO) {
		this.orderBO = orderBO;
		this.orderProductBO = orderProductBO;
	}
	
	// 결제 API
	@PostMapping("/order/confirmation")
	public Map<String, Object> confirmation(
			@RequestParam("paymentId") String paymentId,
			@RequestParam("userId") int userId,
			@RequestParam("totalPrice") int totalPrice,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("productIdList") List<Integer> productIdList) {
		
		// 1. order DB에 저장
		orderBO.addOrder(paymentId, userId, totalPrice, address, phoneNumber);
		
		// 2. orderProduct DB에 저장 (여러건일 수 있음)
		for (int productId : productIdList) {
			orderProductBO.addOrderProduct(paymentId, productId);
		}
		
		// 3. 판매된 상품에 대해서는 판매상태를 "판매중"에서 "판매완료"로 변경해주기 
		for (int productId : productIdList) {
			orderBO.updateSaleStatus(productId);
		}
		
		// return
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
