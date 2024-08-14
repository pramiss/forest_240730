package com.forest.book.bo;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.forest.book.entity.BookEntity;
import com.forest.book.repository.BookRepository;

@Service
public class BookBO {
	
	private BookRepository bookRepository;
	
	public BookBO(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	/**
	 * 도서 추가
	 * @param book
	 */
	public void addBook(Map<String, Object> book) {
		
		BookEntity bookEntity = BookEntity.builder()
				.isbn((String)book.get("isbn"))
				.categoryName((String)book.get("categoryName"))
				.pubDate((String)book.get("pubDate"))
				.priceSales(Integer.parseInt((String)book.get("priceSales")))
				.priceStandard(Integer.parseInt((String)book.get("priceStandard")))
				.customerReviewRank(Double.parseDouble((String)book.get("customerReviewRank")))
				.description((String)book.get("description"))
				.cover((String)book.get("cover"))
				.title((String)book.get("title"))
				.author((String)book.get("author"))
				.link((String)book.get("link"))
				.publisher((String)book.get("publisher"))
				.build();
		
		bookRepository.save(bookEntity);
	} //-- 도서 추가
}
