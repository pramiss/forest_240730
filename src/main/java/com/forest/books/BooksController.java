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
	
	@GetMapping("/books/bestseller")
	public String bestseller(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			Model model) {
		// log.info("%%%" + page);
		
		List<ItemView> itemViewList = booksBO.getBestseller(page);
		
		model.addAttribute("itemList", itemViewList);
		
		return "books/bestseller";
	}
}
