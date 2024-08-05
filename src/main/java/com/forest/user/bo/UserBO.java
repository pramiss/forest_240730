package com.forest.user.bo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.forest.user.domain.User;
import com.forest.user.mapper.UserMapper;

@Service
public class UserBO {
	
	private final UserMapper userMapper;
	
	public UserBO(UserMapper userMapper) {
	    this.userMapper = userMapper;
	}

	// User 가져오기
	public User getUserByLoginIdAndPassword(String loginId, String password) {
		return userMapper.selectUserByLoginIdAndPassword(loginId, password);
	}
	
	
	// User 추가
	public void addUser(String loginId, String password, String name, String phoneNumber, String email) {
		userMapper.insertUser(loginId, password, name, phoneNumber, email);
	}
	
	
	
	
	public List<User> getUserListTest() {
		return userMapper.selectUserListTest();
	}
}
