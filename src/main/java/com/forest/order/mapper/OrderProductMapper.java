package com.forest.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderProductMapper {

	// insert
	public void insertOrderProduct(
			@Param("paymentId") String paymentId, 
			@Param("productId") int productId);
}
