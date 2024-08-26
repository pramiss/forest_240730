package com.forest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class 람다테스트 {

	@Test
	void 람다테스트2() {
		List<String> fruits = List.of("apple", "banana");
		
		fruits = fruits
				.stream()
				.map(element -> element.toUpperCase()) // 람다식 방식
				.collect(Collectors.toList());
	}
	
	@Test
	void 메소드레퍼런스() {
		List<String> fruits = List.of("apple", "banana", "cherry");
		
		fruits = fruits
		.stream() // 지금 꺼낸 요소가 String임을 알고 있음
		.map(String::toUpperCase) // 메소드 레퍼런스 방식
		.collect(Collectors.toList());
		
		log.info("%%% {}", fruits);
	}
}
