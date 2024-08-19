package com.forest.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.books.domain.ItemView;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

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
			Model model) {
		
		// 1. 알라딘 API로 도서 상세 정보를 가져옴
		ItemView itemView = booksBO.getItemLookUp(isbn, "ISBN13");
		
		// 3. 모델에 담음
		model.addAttribute("item", itemView);
		
		return "books/detail";
	} //-- 도서 상세 페이지
}
