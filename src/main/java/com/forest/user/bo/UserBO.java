package com.forest.user.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.cart.bo.CartBO;
import com.forest.cart.entity.CartEntity;
import com.forest.like.bo.LikeBO;
import com.forest.like.entity.LikeEntity;
import com.forest.order.bo.OrderBO;
import com.forest.order.bo.OrderProductBO;
import com.forest.order.domain.Order;
import com.forest.order.domain.OrderProduct;
import com.forest.order.domain.OrderView;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;
import com.forest.user.domain.User;
import com.forest.user.mapper.UserMapper;

@Service
public class UserBO {
	
	private final OrderBO orderBO;
	private final OrderProductBO orderProductBO;
	private final ProductBO productBO;
	private final CartBO cartBO;
	private final LikeBO likeBO;
	private final BooksBO booksBO;
	private final UserMapper userMapper;
	
	public UserBO(UserMapper userMapper, LikeBO likeBO, BooksBO booksBO, 
			CartBO cartBO, ProductBO productBO, OrderBO orderBO, OrderProductBO orderProductBO) {
	    this.userMapper = userMapper;
	    this.likeBO = likeBO;
	    this.booksBO = booksBO;
	    this.cartBO = cartBO;
	    this.productBO = productBO;
	    this.orderBO = orderBO;
	    this.orderProductBO = orderProductBO;
	}

	// User 가져오기
	public User getUserById(int id) {
		return userMapper.selectUserById(id);
	}
	public User getUserByLoginId(String loginId) {
		return userMapper.selectUserByLoginId(loginId);
	}
	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userMapper.selectUserByLoginIdAndPassword(loginId, password);
	}
	
	
	// User 추가
	public void addUser(String loginId, String password, String name, String phoneNumber, String email) {
		userMapper.insertUser(loginId, password, name, phoneNumber, email);
	}
	
	// User 업데이트
	public void updateUserById(int id, String password, String name, String phoneNumber, String email, String address) {
		userMapper.updateUserById(id, password, name, phoneNumber, email, address);
	}
	
	// User의 List<LikeEntity> 조회
	public List<LikeEntity> getLikeListByUserId(int userId) {
		return likeBO.getLikeListByUserId(userId);
	}
	
	
    // User의 List<Cart> 조회
    public List<CartEntity> getCartListByUserId(int userId) {
    	
        return cartBO.getCartListByUserId(userId);
    }
	
    // User의 List<OrderView> 조회
 	public List<OrderView> getOrderViewListByUserId(int userId) {
 		
 		List<OrderView> orderViewList = new ArrayList<>();
 		
 		// 1. User의 order 리스트 가져오기
 		List<Order> orderList = orderBO.getOrderListByIdDesc();
 		
 		// 2. order에 해당하는 orderProuct들 가져오기
 		for (Order order : orderList) {
 			
 			// order에 해당하는 orderProduct를 가져온다.
 			OrderView orderView = new OrderView();
 			List<OrderProduct> orderProductList = orderProductBO.getOrderProductListByPaymentId(order.getPaymentId());
 			
 			// productIdTitle
 			Map<Integer, String> productIdTitle = new HashMap<>();
 			
 			// orderProductList(productId) -> productIdTitle(productId:title)
 			for (OrderProduct orderProduct : orderProductList) {
 				int productId = orderProduct.getProductId();
 				
 				// productId에 해당하는 title을 가져온다.
 				ProductView productView = productBO.getProductView(productId);
 				String title = productView.getBook().getTitle();
 				
 				// productIdTitle에 넣는다.
 				productIdTitle.put(productId, title);
 			}
 			
 			orderView.setOrder(order);
 			orderView.setProductIdName(productIdTitle);
 			orderViewList.add(orderView);
 		}
 		
 		return orderViewList;
 	}
    
    // ProductBO에서 ProductView 가져오기 (by productId)
    public ProductView getProductViewByProductId(int productId) {
        return productBO.getProductView(productId);
    }
    
	// BooksBO의 상품 조회 API 호출
	public ItemView getItemLookUp(String isbn) {
		return booksBO.getItemLookUp(isbn, "ISBN13");
	}
	
	
	
	public List<User> getUserListTest() {
		return userMapper.selectUserListTest();
	}
}
