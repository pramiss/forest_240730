package com.forest.order.domain;

import java.util.Map;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderView {

	private Order order; // 주문 (1개)
	private Map<Integer, String> productIdName; // 해당 주문의 {productId:productName}
}
