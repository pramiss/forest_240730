package com.forest.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.forest.user.domain.User;

@Mapper
public interface UserMapper {

	public List<User> selectUserListTest();
}
