package com.forest.order.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.order.domain.OrderProduct;
import com.forest.order.mapper.OrderProductMapper;

@Service
public class OrderProductBO {

	private final OrderProductMapper orderProductMapper;
	
	public OrderProductBO (OrderProductMapper orderProductMapper) {
		this.orderProductMapper = orderProductMapper;
	}
	
	// orderProduct 추가
	public void addOrderProduct(String paymentId, int productId) {
		orderProductMapper.insertOrderProduct(paymentId, productId);
	}
	
	// order(paymentId)에 해당하는 List<OrderProduct>를 가져오기
	public List<OrderProduct> getOrderProductListByPaymentId(String paymentId) {
		return orderProductMapper.selectOrderProductListByPaymentId(paymentId);
	}
}
