package com.forest.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.forest.user.domain.User;

@Mapper
public interface UserMapper {

	// SELECT
	public User selectUserById(int id);
	public User selectUserByLoginId(String loginId);
	public User selectUserByLoginIdAndPassword(
			@Param("loginId") String loginId, 
			@Param("password") String password);
	public User selectUserByKakaoId(String kakaoId);
	
	
	// INSERT
	public void insertUser(
			@Param("loginId") String loginId,
			@Param("password") String password,
			@Param("name") String name, 
			@Param("phoneNumber") String phoneNumber,
			@Param("email") String email);
	public void insertUserKakao(
			@Param("kakaoId") String kakaoId,
			@Param("name") String name, 
			@Param("phoneNumber") String phoneNumber, 
			@Param("email") String email);
	
	// UPDATE
	public void updateUserById(
			@Param("id") int id, 
			@Param("password") String password, 
			@Param("name") String name, 
			@Param("phoneNumber") String phoneNumber, 
			@Param("email") String email, 
			@Param("address") String address);
	public void updateKakaoUserByKakaoId(
			@Param("kakaoId") String kakaoId,
			@Param("name") String name, 
			@Param("phoneNumber") String phoneNumber, 
			@Param("email") String email, 
			@Param("address") String address);
	
	
	public List<User> selectUserListTest();
}
