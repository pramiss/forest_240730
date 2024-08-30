package com.forest.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forest.order.domain.Order;

@Mapper
public interface OrderMapper {

	// select
	public List<Order> selectOrderListByIdDesc();
	public List<Order> selectOrderListByUserIdByIdDesc(int userId);
	
	// insert
	public void insertOrder(
			@Param("paymentId") String paymentId,
			@Param("userId") int userId,
			@Param("totalPrice") int totalPrice,
			@Param("address") String address,
			@Param("phoneNumber") String phoneNumber);
	
	// update
	public void updateOrderStatus(
			@Param("orderId") int orderId, 
			@Param("status") String status);
}
