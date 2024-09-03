package com.forest.books;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.like.entity.LikeEntity;

import jakarta.servlet.http.HttpSession;
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
	
	public String mainPage() {
		return "books/main";
	}
	
	/**
	 * 베스트셀러 메인 페이지
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping({"/", "/books/bestseller"})
	public String bestseller(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "year", required = false, defaultValue = "0") Integer year,
			@RequestParam(value = "month", required = false, defaultValue = "0") Integer month,
			@RequestParam(value = "week", required = false, defaultValue = "0") Integer week,
			HttpSession session,
			Model model) {

		// queryType
		String queryType = "Bestseller";
		
		// year, month, page
		if (year == 0 && month == 0 && week == 0) {
			LocalDateTime now = LocalDateTime.now();
			year = now.getYear();
			month = now.getMonthValue();
			week = now.getDayOfMonth() / 7 + 1;
		}
		
		// itemView - item, productList, like
		List<ItemView> itemViewList = booksBO.getBestsellerList(queryType, page, year, month, week);
		
		// likeIsbnList
		Integer userId = (Integer)session.getAttribute("userId");
		List<String> likeIsbnList = new ArrayList<>();
		
		if (userId != null) {
			// 로그인 유저의 좋아요 리스트
			List<LikeEntity> likeEntityList = booksBO.getLikeListByUserId(userId);
			for (LikeEntity likeEntity : likeEntityList) {
				likeIsbnList.add(likeEntity.getId().getIsbn());
			}
		} // 로그인 상태가 아닌 경우에는 조회X
		model.addAttribute("likeIsbnList", likeIsbnList);
		
		model.addAttribute("pageIndex", page);
		model.addAttribute("itemList", itemViewList);
		
		// 베스트셀러 날짜
		Map<String, Integer> date = ItemView.getQueryDate();
		model.addAttribute("date", date);
		
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
			HttpSession session,
			Model model) {
		
		List<ItemView> itemViewList = booksBO.getItemNewList(queryType, page);
		
		// likeIsbnList
		Integer userId = (Integer)session.getAttribute("userId");
		List<String> likeIsbnList = new ArrayList<>();
		
		if (userId != null) {
			// 로그인 유저의 좋아요 리스트
			List<LikeEntity> likeEntityList = booksBO.getLikeListByUserId(userId);
			for (LikeEntity likeEntity : likeEntityList) {
				likeIsbnList.add(likeEntity.getId().getIsbn());
			}
		} // 로그인 상태가 아닌 경우에는 조회X
		model.addAttribute("likeIsbnList", likeIsbnList);
		
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
			HttpSession session,
			Model model) {
		
		List<ItemView> itemViewList = booksBO.getItemSearch(query, queryType, page);
		
		// likeIsbnList
		Integer userId = (Integer)session.getAttribute("userId");
		List<String> likeIsbnList = new ArrayList<>();
		
		if (userId != null) {
			// 로그인 유저의 좋아요 리스트
			List<LikeEntity> likeEntityList = booksBO.getLikeListByUserId(userId);
			for (LikeEntity likeEntity : likeEntityList) {
				likeIsbnList.add(likeEntity.getId().getIsbn());
			}
		} // 로그인 상태가 아닌 경우에는 조회X
		model.addAttribute("likeIsbnList", likeIsbnList);
		
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
	
	/**
	 * 도서 상세 페이지 (REST Api)
	 * @param isbn
	 * @param model
	 * @return
	 */
	@GetMapping("/books/detail/{isbn}")
	public String detailBooks(
			@PathVariable(name = "isbn") String isbn,
			HttpSession session,
			Model model) {
		
		// 1. 알라딘 API로 도서 상세 정보를 가져옴
		ItemView itemView = booksBO.getItemLookUp(isbn, "ISBN13");
		
		// 2. 좋아요 여부 확인
		Integer userId = (Integer)session.getAttribute("userId");
		boolean isLiked = false;
		
		if (userId != null) {
			// 로그인 유저의 좋아요 리스트
			isLiked = booksBO.isLikeById(userId, isbn);
		}
		
		// 3. 모델에 담음
		model.addAttribute("isLiked", isLiked);
		model.addAttribute("item", itemView);
		
		return "books/detail";
	} //-- 도서 상세 페이지
	
}
