package com.forest.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.admin.bo.AdminBO;
import com.forest.books.domain.ItemView;
import com.forest.order.domain.OrderView;
import com.forest.product.entity.ProductEntity;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private AdminBO adminBO;
	
	public AdminController(AdminBO adminBO) {
		this.adminBO = adminBO;
	}
	
	// 관리자 페이지 - 중고도서 리스트 (여기가 메인페이지)
	@GetMapping("/product-list")
	public String productList(Model model) {
		
		// product 리스트를 받아옴 + model에 추가
		List<ProductEntity> productList = adminBO.getProductList();
		model.addAttribute("productList", productList);
		
		// 페이지로.
		return "admin/productList";
	} //-- 중고도서 리스트
	
	// 중고도서 업로드 페이지
	@GetMapping("/upload-product")
	public String uploadProduct() {
		
		return "admin/uploadProduct";
	} //-- 중고도서 업로드 페이지
	
	// 중고도서 업로드 페이지 - 도서 가져오기 API
	@GetMapping("/upload-product/find-item")
	public String findItem(
		@RequestParam("itemId") String itemId,
		@RequestParam("itemIdType") String itemIdType,
		Model model) {
		
		ItemView itemView = adminBO.getItemView(itemId, itemIdType);
		
		if (itemView == null) {
			// 여기서 error.html에서 error 문구 반환
			return "error";
		}
		
		model.addAttribute("item", itemView);
		
		return "admin/bookDetailAdmin";
	}
	
	// 주문목록 페이지
	@GetMapping("/order-list")
	public String orderList(Model model) {
		
		// 모든 OrderView 가져오기
		List<OrderView> orderViewList = adminBO.getOrderViewList();
		model.addAttribute("orderViewList", orderViewList);
		
		// 페이지로.
		return "admin/orderList";
	}
}
