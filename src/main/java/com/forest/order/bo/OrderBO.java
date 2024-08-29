package com.forest.order.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.cart.bo.CartBO;
import com.forest.order.domain.Order;
import com.forest.order.mapper.OrderMapper;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

import jakarta.transaction.Transactional;

@Service
public class OrderBO {

	private final CartBO cartBO;
	private final ProductBO productBO;
	private final OrderMapper orderMapper;
	
	public OrderBO(ProductBO productBO, OrderMapper orderMapper, CartBO cartBO) {
		this.productBO = productBO;
		this.orderMapper = orderMapper;
		this.cartBO = cartBO;
	}
	
	// select : 모든 order를 가져온다. - id 내림차순
	public List<Order> getOrderListByIdDesc() {
		return orderMapper.selectOrderListByIdDesc();
	}
	
	// select : List<ProductView> 가져오기 - ProductBO
	public List<ProductView> getProductViewList(List<Integer> productIdList) {
		
		List<ProductView> productViewList = new ArrayList<>();
		
		for (Integer productId : productIdList) {
			productViewList.add(productBO.getProductView(productId));
		}
		
		return productViewList;
	}
	
	// insert : order 저장
	public void addOrder(String paymentId, int userId, int totalPrice, String address, String phoneNumber) {
		orderMapper.insertOrder(paymentId, userId, totalPrice, address, phoneNumber);
	}
	
	// update : order의 배송상태를 status로 변경한다.
	public void updateOrderStatus(int orderId, String status) {
		orderMapper.updateOrderStatus(orderId, status);
	}
	
	// update : Product의 판매상태를 "판매완료"로 바꾼다 - ProductBO
	@Transactional
	public void updateSaleStatus(int productId) {
		
		// 1. 상품의 상태를 "판매완료"로 변경
		productBO.updateSaleStatus(productId);
		
		// 2. "판매완료"된 상품은 cart에서 삭제된다.
		cartBO.deleteCartByProductId(productId);
	}

}
