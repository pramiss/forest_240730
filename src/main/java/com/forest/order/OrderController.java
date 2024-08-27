package com.forest.order;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.order.bo.OrderBO;
import com.forest.product.entity.ProductView;

@Controller
public class OrderController {

	private final OrderBO orderBO;
	
	public OrderController(OrderBO orderBO) {
		this.orderBO = orderBO;
	}
	
	/**
	 * 주문 페이지로 이동
	 * @param orderProductList
	 * @param orderPrice
	 * @param model
	 * @return
	 */
	@PostMapping("/order/check-out")
	public String checkOut(
			@RequestParam(name = "orderProductList") List<Integer> orderProductList,
			@RequestParam(name = "orderPrice") Integer orderPrice,
			Model model) {
		
		// orderProductList를 기반으로 List<ProductView>를 만들기
		List<ProductView> productViewList = orderBO.getProductViewList(orderProductList);
		
		// model에 담고 return
		model.addAttribute("productViewList", productViewList);
		model.addAttribute("price", orderPrice);
		return "order/checkOut";
	}
}
