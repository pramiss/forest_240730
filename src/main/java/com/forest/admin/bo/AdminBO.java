package com.forest.admin.bo;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.forest.book.bo.BookBO;
import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.product.bo.ProductBO;

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
	public void addProductAndBook(Map<String, Object> product, Map<String, Object> book) {
		
		// 상품 추가
		productBO.addProduct(product); 
		
		// 도서 추가
		bookBO.addBook(book);
	} //-- 도서&상품 추가 API
}
