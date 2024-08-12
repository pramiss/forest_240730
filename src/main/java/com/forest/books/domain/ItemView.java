package com.forest.books.domain;

import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

//도서 리스트, 상세 페이지에서 필요한 내용 기반

@ToString
@Data
public class ItemView {
	// 총 아이템 수 - @Data가 static 변수에 대한 getter/setter는 만들어주지않음
	private static int totalResults = 0; // 총 개수
	
	public static int getTotalResults() {
		return totalResults;
	}
	public static void setTotalResults(int totalResults) {
		ItemView.totalResults = totalResults;
	}
	
	// 알라딘 도서 상품 정보
	private String isbn13;
	private String link; // 상품링크URL(알라딘링크)
	private String cover; // 커버(표지 이미지)
	private String title; // 상품명(책제목) - 길면 자르고 넣기
	private String author; // 저자/아티스트(지은이) - 근데 다른 것도 많아서 가공하고 넣어야함
	private String publisher; //출판사(제작사/출시사) - 문자열
	private String categoryName;
	private LocalDate pubDate; // 출간일(출시일)
	private String description; // 상품설명(요약)
	private int priceSales; // 판매가
	private int priceStandard; // 정가
	private double customerReviewRank; // 회원 리뷰 평점(별점 평균) : 0~10점(별0.5개당 1점)
	private Integer bestRank; // (베스트셀러인 경우만 노출) 베스트셀러 순위 정보 - 정수
	
	// TODO: 중고도서 정보 - 있는지 없는지, 중고가, 해당 중고도서 상세 링크
	
	// TODO: 좋아요
	
	// TODO: 장바구니
	
}
