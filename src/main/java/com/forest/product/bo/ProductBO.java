package com.forest.product.bo;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.forest.product.entity.ProductEntity;
import com.forest.product.repository.ProductRepository;

@Service
public class ProductBO {
	
	private ProductRepository productRepository;
	
	public ProductBO(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// 상품 추가
	public void addProduct(Map<String, Object> product) {
		ProductEntity productEntity = ProductEntity.builder()
				.isbn((String)product.get("isbn"))
				.price(Integer.parseInt((String)product.get("price")))
				.bookStatus((String)product.get("bookStatus"))
				.saleStatus((String)product.get("saleStatus"))
				.imageUrl((String)product.get("imageUrl"))
				.build();
		
		productRepository.save(productEntity);
	} //-- 상품 추가
}
