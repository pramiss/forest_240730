package com.forest.cart.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.cart.entity.CartEntity;
import com.forest.cart.entity.CartId;
import com.forest.cart.repository.CartRepository;
import com.forest.product.bo.ProductBO;

@Service
public class CartBO {

	private final ProductBO productBO;
	private final CartRepository cartRepository;
	
	public CartBO(CartRepository cartRepository, ProductBO productBO) {
		this.cartRepository = cartRepository;
		this.productBO = productBO;
	}
	
	// 장바구니 조회 by Id (단건 CartEntity or null)
	public CartEntity getCartById(int userId, int productId) {
		
		CartId cartId = new CartId(userId, productId);
		return cartRepository.findById(cartId).orElse(null);
	}
	
	// 장바구니 조회 (여러건 by UserId)
	public List<CartEntity> getCartListByUserId(int userId) {
		
		return cartRepository.findAllByCartIdUserId(userId);
	}
	
	// 장바구니 추가 (성공: true, 실패: false)
	public boolean addCart(int userId, int productId) {
		
		// Product가 판매중인 상태인지 확인
		String saleStatus = productBO.getProdcutSaleStatus(productId);
		if (saleStatus.equals("판매완료")) {
			return false;
		}
		
		// CartId 생성
		CartId cartId = new CartId(userId, productId);
		
		// CartEntity 생성
		CartEntity cartEntity = CartEntity.builder().cartId(cartId).build();
		
		// Cart에 추가
		cartRepository.save(cartEntity);
		
		return true;
	} //-- 장바구니 추가

	// 장바구니 삭제
	public void deleteCartList(int userId, List<Integer> productIdList) {
		
		for(Integer productId : productIdList) {
			CartId cartId = new CartId(userId, productId);
			CartEntity cartEntity = CartEntity.builder().cartId(cartId).build();
			cartRepository.delete(cartEntity);
		}
		
	} //-- 장바구니 삭제
	
}
