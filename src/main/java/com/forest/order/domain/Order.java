package com.forest.order.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Order {

	// field
	private int id;
	private String paymentId;
	private int userId;
	private int totalPrice;
	private String address;
	private String phoneNumber;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
