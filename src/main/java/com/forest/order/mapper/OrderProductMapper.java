package com.forest.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forest.order.domain.OrderProduct;

@Mapper
public interface OrderProductMapper {

	// select
	public List<OrderProduct> selectOrderProductListByPaymentId(String paymentId);
	
	// insert
	public void insertOrderProduct(
			@Param("paymentId") String paymentId, 
			@Param("productId") int productId);
}
