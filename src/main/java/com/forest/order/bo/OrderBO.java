package com.forest.order.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;

@Service
public class OrderBO {

	private final ProductBO productBO;
	
	public OrderBO(ProductBO productBO) {
		this.productBO = productBO;
	}
	
	// List<ProductView> 가져오기
	public List<ProductView> getProductViewList(List<Integer> productIdList) {
		
		List<ProductView> productViewList = new ArrayList<>();
		
		for (Integer productId : productIdList) {
			productViewList.add(productBO.getProductView(productId));
		}
		
		return productViewList;
	}
}
