package com.forest.product.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forest.common.FileManagerService;
import com.forest.product.entity.ProductEntity;
import com.forest.product.repository.ProductRepository;

@Service
public class ProductBO {
	
	private ProductRepository productRepository;
	private FileManagerService fileManagerService;
	
	public ProductBO(ProductRepository productRepository, FileManagerService fileManagerService) {
		this.productRepository = productRepository;
		this.fileManagerService = fileManagerService;
	}

	// 상품 추가
	public void addProduct(Map<String, Object> product, MultipartFile file) {
		
		
		// 1. ImageUrl(사실은 사진파일)을 로컬에 저장하고 주소 받아오기
		String imageUrl = fileManagerService.uploadFile(file, (String)product.get("isbn"));
		
		// 2. Product Entity 만든 후 추가
		ProductEntity productEntity = ProductEntity.builder()
				.isbn((String)product.get("isbn"))
				.price(Integer.parseInt((String)product.get("price")))
				.bookStatus((String)product.get("bookStatus"))
				.saleStatus((String)product.get("saleStatus"))
				.imageUrl(imageUrl)
				.build();
		
		productRepository.save(productEntity);
	} //-- 상품 추가
	
	// 상품리스트 가져오기
	public List<ProductEntity> getProductList() {
		return productRepository.findAllByOrderByIdDesc();
	} //-- 상품리스트 가져오기
	
	// 상품 업데이트
	public void updateProduct(Map<String, String> saleStatus) {
		
		for (String productId : saleStatus.keySet()) {
			// 1. find
			ProductEntity product = productRepository.findById(Integer.parseInt(productId)).orElse(null);
			
			// 2. setter(builder)
			ProductEntity updatedProduct =
					product.toBuilder().saleStatus(saleStatus.get(productId)).build();
			
			// 3. save
			productRepository.save(updatedProduct);
		}
		
	} // 상품 업데이트
}
