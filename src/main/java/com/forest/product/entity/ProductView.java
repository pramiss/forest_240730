package com.forest.product.entity;

import com.forest.book.entity.BookEntity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ProductView {
	private ProductEntity product;
	private BookEntity book;
	
	// TODO: 좋아요, 장바구니
}
