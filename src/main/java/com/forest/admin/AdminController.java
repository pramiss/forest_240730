package com.forest.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	// 관리자 페이지 - 중고도서 리스트
	@GetMapping("/product-list")
	public String productList() {
		
		return "admin/productList";
	} //-- 중고도서 리스트


	
}
