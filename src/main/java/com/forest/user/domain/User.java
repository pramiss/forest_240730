package com.forest.user.domain;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
	// field
	private int id;
	private String loginId;
	private String password;
	private String authType;
	private String kakaoId;
	private String name;
	private String phoneNumber;
	private String email;
	private String address;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
