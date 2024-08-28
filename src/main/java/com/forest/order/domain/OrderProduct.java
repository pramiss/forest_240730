package com.forest.order.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data // setter, getter 등 생성
public class OrderProduct {

	// field
	private int id;
	private String paymentId;
	private int productId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
