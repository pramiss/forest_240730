package com.forest.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

	@PostMapping("/order/check-out")
	public String checkOut() {
		
		return "order/checkOut";
	}
}
