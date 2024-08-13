package com.forest.book.entity;

import java.time.LocalDate;

public class BookEntity {

	private String isbn13;
	private String title; // 책 제목
	private String author; // 글쓴이
	private String link; // 상품 알라딘 링크
	private String cover; // 커버(표지 이미지)
	private String publisher; //출판사(제작사/출시사) - 문자열
	private String categoryName; // 카테고리
	private LocalDate pubDate; // 출간일(출시일)
	private String description; // 상품설명(요약)
	private int priceSales; // 판매가
	private int priceStandard; // 정가
	private double customerReviewRank; // 회원 리뷰 평점(별점 평균) : 0~10점(별0.5개당 1점)
	private Integer bestRank; 
}
