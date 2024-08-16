package com.forest.admin.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forest.book.bo.BookBO;
import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductEntity;

@Service
public class AdminBO {

	private BooksBO booksBO;
	private ProductBO productBO;
	private BookBO bookBO;
	
	public AdminBO(BooksBO booksBO, ProductBO productBO, BookBO bookBO) {
		this.booksBO = booksBO;
		this.productBO = productBO;
		this.bookBO = bookBO;
	}
	
	/**
	 * 도서 정보 API
	 * @param itemId
	 * @param itemIdType
	 * @return
	 */
	public ItemView getItemView(String itemId, String itemIdType) {
		return booksBO.getItemLookUp(itemId, itemIdType);
	} //-- 도서 정보 API
	
	/**
	 * 도서&상품 추가 API
	 * @param product
	 * @param book
	 */
	public void addProductAndBook(
			Map<String, Object> product, Map<String, Object> book, MultipartFile file) {
		
		// 상품 추가
		productBO.addProduct(product, file); 
		
		// 도서 추가
		bookBO.addBook(book);
	} //-- 도서&상품 추가 API
	
	/**
	 * 상품 리스트 가져오기
	 * @return
	 */
	public List<ProductEntity> getProductList() {
		return productBO.getProductList();
	} //-- 상품 리스트 가져오기
	
	// 상품 판매상태 업데이트
	public void updateProduct(Map<String, String> saleStatus) {
		productBO.updateProduct(saleStatus);
	} //-- 상품 판매상태 업데이트
}
