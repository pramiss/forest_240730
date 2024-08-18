package com.forest.books;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	/**
	 * 메인 페이지
	 * @return
	 */
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
	
	/**
	 * 검색 결과 페이지
	 * @param query
	 * @param queryType
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/books/search")
	public String searchBooks( // query: 검색어, queryType: 검색어 종류, page: 시작페이지
			@RequestParam("query") String query,
			@RequestParam(value = "queryType", required = false, defaultValue = "Keyword") String queryType,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			Model model) {
		
		List<ItemView> itemViewList = booksBO.getItemSearch(query, queryType, page);
		
		// model에 담기
		model.addAttribute("query", query);
		model.addAttribute("queryType", queryType);
		model.addAttribute("pageIndex", page);
		
		int lastBtnIndex = ItemView.getTotalResults() / 10 + 1;
		if (lastBtnIndex > 20) { lastBtnIndex = 20; }
		model.addAttribute("lastBtnIndex", lastBtnIndex);
		
		int leftBtnIndex = Integer.parseInt(page) - 3;
		if (leftBtnIndex < 1) { leftBtnIndex = 1; }
		model.addAttribute("leftBtnIndex", leftBtnIndex);

		int rightBtnIndex = Integer.parseInt(page) + 3;
		if (rightBtnIndex > lastBtnIndex) { rightBtnIndex = lastBtnIndex; }
		model.addAttribute("rightBtnIndex", rightBtnIndex);
		
		model.addAttribute("itemList", itemViewList);
		model.addAttribute("totalResults", ItemView.getTotalResults());
		
		return "books/search";
	} //-- 검색 페이지
	
	// 도서 상세 페이지 (REST Api)
	@GetMapping("/books/detail/{isbn}")
	public String detailBooks(
			@PathVariable(name = "isbn") String isbn,
			Model model) {
		
		// 1. 알라딘 API로 도서 상세 정보를 가져옴
		ItemView itemView = booksBO.getItemLookUp(isbn, "ISBN13");
		
		// 2. itemView에 product를 담음 (존재한다면)
		
		
		// 3. 모델에 담음
		model.addAttribute("itemView", itemView);
		
		return "books/detail";
	} //-- 도서 상세 페이지
	
}
