package com.forest.book.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.forest.book.entity.BookEntity;
import com.forest.book.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookBO {
	
	private BookRepository bookRepository;
	
	public BookBO(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	// 도서 조회
	public List<BookEntity> getBookList() {
		return bookRepository.findAll();
	} //-- 도서 조회
	
	/**
	 * 도서 추가
	 * @param book
	 */
	public void addBook(Map<String, Object> book) {
		
		// 1. 도서 정보 조회 (이미 해당 도서가 있을 경우 다시 저장하지 않기 위함)
		BookEntity bookEntity = bookRepository.findById((String)book.get("isbn")).orElse(null);
		
		if (bookEntity != null) {
			log.info("***** 추가되지 않음!!");
			return;
		}
		
		// 2. 도서 정보 추가
		log.info("***** 도서 추가 ISBN");
		
		String pubDateString = (String)book.get("pubDate");
		LocalDate pubDate = LocalDate.parse(pubDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		bookEntity = BookEntity.builder()
				.isbn((String)book.get("isbn"))
				.categoryName((String)book.get("categoryName"))
				.pubDate(pubDate)
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
	
	// 도서 삭제
	public void deleteBookById(String isbn) {
		bookRepository.deleteById(isbn);
	} //-- 도서 삭제
}
