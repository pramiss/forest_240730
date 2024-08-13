package com.forest.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private BooksBO booksBO;
	
	public AdminController(BooksBO booksBO) {
		this.booksBO = booksBO;
	}
	
	// 관리자 페이지 - 중고도서 리스트 (여기가 메인페이지)
	@GetMapping("/product-list")
	public String productList() {
		
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
		
		ItemView itemView = booksBO.getItemLookUp(itemId, itemIdType);
		
		if (itemView == null) {
			// 여기서 false로 주면 안됨. false.html 찾으러 감
			return "false";
		}
		
		model.addAttribute("item", itemView);
		
		return "admin/bookDetailAdmin";
	}
}
