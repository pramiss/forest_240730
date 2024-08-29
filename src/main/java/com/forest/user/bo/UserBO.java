package com.forest.user.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.cart.bo.CartBO;
import com.forest.cart.entity.CartEntity;
import com.forest.like.bo.LikeBO;
import com.forest.like.entity.LikeEntity;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductView;
import com.forest.user.domain.User;
import com.forest.user.mapper.UserMapper;

@Service
public class UserBO {
	
	private final ProductBO productBO;
	private final CartBO cartBO;
	private final LikeBO likeBO;
	private final BooksBO booksBO;
	private final UserMapper userMapper;
	
	public UserBO(UserMapper userMapper, LikeBO likeBO, BooksBO booksBO, CartBO cartBO, ProductBO productBO) {
	    this.userMapper = userMapper;
	    this.likeBO = likeBO;
	    this.booksBO = booksBO;
	    this.cartBO = cartBO;
	    this.productBO = productBO;
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
