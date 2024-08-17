package com.forest.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

@RequestMapping("/product")
@Controller
public class ProductController {

	private final ProductBO productBO;
	
	public ProductController (ProductBO productBO) {
		this.productBO = productBO;
	}
	
	// 중고 도서 목록 페이지
	@GetMapping("/product-list")
	public String productList(Model model) {
		
		// 1. 전체 상품을 가져오기
		List<ProductView> productViewList = productBO.getProductViewList();
		
		// 2. model에 담기
		model.addAttribute("productViewList", productViewList);
		
		return "product/productList";
	} //-- 중고 도서 목록 페이지
}
