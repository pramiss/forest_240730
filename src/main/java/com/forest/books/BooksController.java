package com.forest.books;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BooksController {
	
	private final BooksBO booksBO;
	
	public BooksController(BooksBO booksBO) {
		this.booksBO = booksBO;
	}

	@GetMapping("/")
	public String mainPage() {
		return "books/main";
	}
	
	/**
	 * 베스트셀러 메인 페이지
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/books/bestseller")
	public String bestseller(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			Model model) {
		// log.info("%%%" + page);
		
		String queryType = "Bestseller";
		
		List<ItemView> itemViewList = booksBO.getItemList(queryType, page);
		
		model.addAttribute("pageIndex", page);
		model.addAttribute("itemList", itemViewList);
		
		return "books/bestseller";
	} //-- 베스트셀러 페이지
	
	/**
	 * 신간 메인 페이지
	 * @param queryType
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/books/new")
	public String newBooks(
			@RequestParam(value = "queryType", required = false, defaultValue = "ItemNewSpecial") String queryType,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			Model model) {
		
		List<ItemView> itemViewList = booksBO.getItemList(queryType, page);
		
		model.addAttribute("queryType", queryType);
		model.addAttribute("pageIndex", page);
		model.addAttribute("itemList", itemViewList);
		
		return "books/new";
	} //-- 신간 페이지
	
}
