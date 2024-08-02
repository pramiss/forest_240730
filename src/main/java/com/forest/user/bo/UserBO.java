package com.forest.user.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forest.user.domain.User;
import com.forest.user.mapper.UserMapper;

@Service
public class UserBO {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> getUserListTest() {
		return userMapper.selectUserListTest();
	}
}
