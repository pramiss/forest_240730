package com.forest.order.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.order.domain.Order;
import com.forest.order.mapper.OrderMapper;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

@Service
public class OrderBO {

	private final ProductBO productBO;
	private final OrderMapper orderMapper;
	
	public OrderBO(ProductBO productBO, OrderMapper orderMapper) {
		this.productBO = productBO;
		this.orderMapper = orderMapper;
	}
	
	// 모든 order를 가져온다. - id 내림차순
	public List<Order> getOrderListByIdDesc() {
		return orderMapper.selectOrderListByIdDesc();
	}
	
	// order 저장
	public void addOrder(String paymentId, int userId, int totalPrice, String address, String phoneNumber) {
		orderMapper.insertOrder(paymentId, userId, totalPrice, address, phoneNumber);
	}
	
	// List<ProductView> 가져오기 - ProductBO
	public List<ProductView> getProductViewList(List<Integer> productIdList) {
		
		List<ProductView> productViewList = new ArrayList<>();
		
		for (Integer productId : productIdList) {
			productViewList.add(productBO.getProductView(productId));
		}
		
		return productViewList;
	}
	
	// Product의 판매상태를 "판매완료"로 바꾼다 - ProductBO
	public void updateSaleStatus(int productId) {
		productBO.updateSaleStatus(productId);
	}
}
