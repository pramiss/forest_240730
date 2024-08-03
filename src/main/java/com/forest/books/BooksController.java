package com.forest.books;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksController {

	@GetMapping("/")
	public String mainPage() {
		return "books/main";
	}
	
	@GetMapping("/books/bestseller")
	public String bestseller() {
		return "books/bestseller";
	}
}
