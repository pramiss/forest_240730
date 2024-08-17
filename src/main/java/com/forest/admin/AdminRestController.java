package com.forest.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	 * 도서&상품 추가
	 * @param productString
	 * @param bookString
	 * @return
	 */
	@PostMapping("/add-product-book")
	public Map<String, Object> addProductBook(
			@RequestParam("productString") String productString,
			@RequestParam("bookString") String bookString,
			@RequestParam("file") MultipartFile file) {
		
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
		
        adminBO.addProductAndBook(product, book, file);
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	} //-- 도서&상품 업로드
	
	// 상품 업데이트
	@PutMapping("/update-product")
	public Map<String, Object> updateProduct(
			@RequestParam("saleStatusString") String saleStatusString) {
		
		// 1. 받아온 변경 saleStatus 문자열 데이터를 Map으로 변경
		
		ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> saleStatus = null;
		
        try {
        	saleStatus = objectMapper.readValue(saleStatusString, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//		log.info("***** saleStatus : " +  saleStatus);
		
        // 2. saleStatus 업데이트
        adminBO.updateProduct(saleStatus);
        
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		return result;
	} //-- 상품 업데이트
	
	// 상품 삭제 API
	@DeleteMapping("/delete-product")
	public Map<String, Object> deleteProduct(
			@RequestParam("productIdList") List<String> productIdList) {
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			adminBO.deleteProduct(productIdList);
		} catch (Exception e) {
			result.put("code", 501);
            result.put("error_message", e.getMessage());
        }
		
		result.put("code", 200);
		return result;
	} //-- 상품 삭제 API
}
