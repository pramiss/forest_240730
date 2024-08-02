package com.forest.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

@Controller
public class TestController {

	@Autowired
	private UserBO userBO;
	
	@ResponseBody
	@RequestMapping("/test1")
	public String test1() {
		return "Hello World!";
	}
	
	@ResponseBody
	@RequestMapping("/test2")
	public Map<String, Object> test2() {
		Map<String, Object> result = new HashMap<>();
		result.put("title", "Spring");
		result.put("version", 4.0);
		result.put("content", "project");
		
		return result;
	}
	
	@RequestMapping("/test3")
	public String test3() {
		return "test/test";
	}
	
	@ResponseBody
	@RequestMapping("/test4")
	public List<User> test4() {
		return userBO.getUserListTest();
	}
}
