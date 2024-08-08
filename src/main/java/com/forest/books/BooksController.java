package com.forest.books;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;

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
	public String bestseller(Model model) {
		
		List<ItemView> itemViewList = booksBO.getBestseller();
		
		model.addAttribute("itemList", itemViewList);
		
		return "books/bestseller";
	}
}
