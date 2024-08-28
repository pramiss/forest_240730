package com.forest.order.domain;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderView {

	private Order order; // 주문 (1개)
	private List<OrderProduct> orderProductList; // 해당 주문의 상품 (여러개 가능)
}
