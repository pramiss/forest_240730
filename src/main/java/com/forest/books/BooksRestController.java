package com.forest.books;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BooksRestController {
	
	@GetMapping("/books/bestseller")
	public Map<String, Object> bestseller() {
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		
	} //-- bestseller

}
