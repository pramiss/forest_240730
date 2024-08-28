package com.forest.product.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forest.book.bo.BookBO;
import com.forest.book.entity.BookEntity;
import com.forest.book.repository.BookRepository;
import com.forest.common.FileManagerService;
import com.forest.like.bo.LikeBO;
import com.forest.product.entity.ProductEntity;
import com.forest.product.entity.ProductView;
import com.forest.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductBO {
	
	private final LikeBO likeBO;
	private final BookBO bookBO;
	private final ProductRepository productRepository;
	private final FileManagerService fileManagerService;
	
	public ProductBO(ProductRepository productRepository, 
			FileManagerService fileManagerService, BookBO bookBO, LikeBO likeBO) {
		this.productRepository = productRepository;
		this.fileManagerService = fileManagerService;
		this.bookBO = bookBO;
		this.likeBO = likeBO;
	}

	// 상품리스트 가져오기 (전체, id순서)
	public List<ProductEntity> getProductList() {
		return productRepository.findAllByOrderByIdDesc();
	} //-- 상품리스트 가져오기
	
	// 상품리스트 조회 (by Isbn, 여러건일 수 있음)
	public List<ProductEntity> getProductListByIsbn(String isbn) {
		return productRepository.findByIsbnOrderByPriceDesc(isbn);
	} //-- 상품 조회 (by Isbn, 여러건일 수 있음)

	// 상품view 조회 (by productId, 단건)
	public ProductView getProductView(int productId) {
		ProductView productView = new ProductView();
		ProductEntity product = productRepository.findById(productId).orElse(null);
		
		productView.setProduct(product); // ProductView - product
		productView.setBook(bookBO.getBook(product.getIsbn())); // ProductView - book
		
		return productView;
	} //-- 상품view 조회
	
	// 상품view 리스트 조회 (전체)
	public List<ProductView> getProductViewList() {

		List<ProductView> productViewList = new ArrayList<>();
		
		// 1. 모든 product를 list로 가져온다. (select)
        List<ProductEntity> productList = productRepository.findAllByOrderBySaleStatusDescIsbnDesc();
		
		// 2. 모든 book을 list로 가져온다. (select)
		List<BookEntity> bookList = bookBO.getBookList();
		
		// 3. 반복문을 돌면서 product의 isbn에 해당하는 book을 bookList에서 찾는다.
		for (ProductEntity product : productList) {

			// 4-1. productView에 product, book을 넣는다.
			ProductView productView = new ProductView();
			
			BookEntity book = bookList.stream()
					.filter(bookEntity -> bookEntity.getIsbn().equals(product.getIsbn()))
					.findFirst()
					.orElse(null);
			
			productView.setProduct(product);
			productView.setBook(book);
			
			// 4-2. productView를 list에 추가
			productViewList.add(productView);
		}
		
		// 5. productView 리스트 반환
		return productViewList;
		
	} //-- 상품view 리스트 조회 (전체)
	
	
	
	
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
		
	} // 상품 업데이트 (리스트)
	
	public void updateSaleStatus(int productId) {
		// 1. find
		ProductEntity product = productRepository.findById(productId).orElse(null);
		
		// 2. setter(builder)
		ProductEntity updatedProduct =
				product.toBuilder().saleStatus("판매완료").build();
		
		// 3. save
		productRepository.save(updatedProduct);
	} //-- 상품 1개를 "판매중" -> "판매완료"로 변경
	
	// 상품 삭제 (삭제한 상품의 isbn 리턴)
	public String deleteProduct(int productId) {
		
		// 1. 해당 상품 가져오기
		ProductEntity product = productRepository.findById(productId).orElse(null);
		
		if (product == null) {
			log.info("***** 삭제할 상품 없음, product id : " + productId);
			return "false";
		}
		
		// 2. isbn, imageUrl 번호 추출
		String isbn = product.getIsbn();
		String imageUrl = product.getImageUrl();
		
		// 3. product 삭제
		productRepository.delete(product);
		
		// 4. imageUrl 삭제
		fileManagerService.deleteFile(imageUrl);
		
		return isbn;
	} //-- 상품 삭제
	
    // 상품의 판매상태 확인 (판매중, 판매완료)
    public String getProdcutSaleStatus(int productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        return productEntity.getSaleStatus();
    }
	
	// 유저, 책의 좋아요 여부 확인 (단건, boolean)
	public boolean isLikeById(int userId, String isbn) {
		return likeBO.isLikeById(userId, isbn);
	}
}
