package com.forest.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forest.user.domain.User;

@Mapper
public interface UserMapper {

	// SELECT
	public User selectUserById(int id);
	public User selectUserByLoginIdAndPassword(
			@Param("loginId") String loginId, 
			@Param("password") String password);
	
	// INSERT
	public void insertUser(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("name") String name, 
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email);
	
	public List<User> selectUserListTest();
}
