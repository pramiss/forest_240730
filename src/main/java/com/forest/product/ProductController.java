package com.forest.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/product")
@Controller
public class ProductController {

	private final ProductBO productBO;
	
	public ProductController (ProductBO productBO) {
		this.productBO = productBO;
	}
	
	/**
	 * 상품(중고도서) 목록 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/product-list")
	public String productList(Model model) {
		
		// 1. 전체 상품을 가져오기
		List<ProductView> productViewList = productBO.getProductViewList();
		
		// 2. model에 담기
		model.addAttribute("productViewList", productViewList);
		
		return "product/productList";
	} //-- 중고 도서 목록 페이지
	
	/**
	 * 상품 상세 페이지
	 * @param isbn
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{productId}")
	public String detailBooks(
			@PathVariable(name = "productId") int productId,
			HttpSession session,
			Model model) {
		
		// 1. 상품 상세 정보를 가져옴
		ProductView productView = productBO.getProductView(productId);
		
		// 2. 좋아요 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		boolean isLiked = false;
		
		if (userId != null) {
			// 로그인 유저의 좋아요 리스트
			isLiked = productBO.isLikeById(userId, productView.getProduct().getIsbn());
		}
		
		// 3. 모델에 담음
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("productView", productView);
		
		return "product/detail";
	} //-- 도서 상세 페이지
}
