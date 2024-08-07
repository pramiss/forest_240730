package com.forest.books.bo;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BooksBO {

	private final WebClient webClient;
	
	public BooksBO(WebClient webClient) {
		this.webClient = webClient;
	}
	
	// 알라딘 OpenAPI : Bestseller
	public Map<String, Object> getBestseller() {
		return webClient.get()
				.uri("https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkkang565081035001&QueryType=Bestseller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101&cover=MidBig")
				.retrieve()
				.bodyToMono(Map.class)
				.block();
	} //-- Bestseller
}
