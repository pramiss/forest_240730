package com.forest.books;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.forest.books.bo.BooksBO;

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
		
		Map<String, Object> bestseller = booksBO.getBestseller();
		
		model.addAttribute("bestseller", bestseller);
		
		return "books/bestseller";
	}
}
