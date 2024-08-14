package com.forest.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forest.admin.bo.AdminBO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminRestController {
	
	private AdminBO adminBO;
	
	public AdminRestController(AdminBO adminBO) {
		this.adminBO = adminBO;
	}

	/**
	 * 도서&상품 업로드
	 * @param productString
	 * @param bookString
	 * @return
	 */
	@PostMapping("/upload")
	public Map<String, Object> upload(
			@RequestParam("productString") String productString,
			@RequestParam("bookString") String bookString) {
		
//		log.info("***** productString : " + productString);
//		log.info("***** bookString : " + bookString);
		
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> product = null;
        Map<String, Object> book = null;
        
        try {
        	product = objectMapper.readValue(productString, new TypeReference<Map<String, Object>>() {});
        	book = objectMapper.readValue(bookString, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

//      log.info("***** product : " + System.identityHashCode(product));
//      log.info("***** book : " + System.identityHashCode(book));
		
        adminBO.addProductAndBook(product, book);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	}
}
