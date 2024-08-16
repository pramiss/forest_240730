package com.forest.book.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Table(name = "book")
@Entity
public class BookEntity {

	@Id
	private String isbn; // isbn13 번호
	
	@Column(name = "categoryName")
	private String categoryName; // 카테고리
	
	@Column(name = "pubDate")
	private LocalDate pubDate; // 출간일(출시일)
	
	@Column(name = "priceSales")
	private int priceSales; // 판매가
	
	@Column(name = "priceStandard")
	private int priceStandard; // 정가
	
	@Column(name = "customerReviewRank")
	private double customerReviewRank; // 회원 리뷰 평점(별점 평균) : 0~10점(별0.5개당 1점)
	
	private String description; // 상품설명(요약)
	private String cover; // 커버(표지 이미지)
	private String title; // 책 제목
	private String author; // 글쓴이
	private String link; // 상품 알라딘 링크
	private String publisher; //출판사(제작사/출시사) - 문자열
	
	@CreationTimestamp
	@Column(name = "createdAt")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updatedAt")
	private LocalDateTime updatedAt;
}
