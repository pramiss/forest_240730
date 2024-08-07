package com.forest.books.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.forest.books.domain.ItemView;

@Service
public class BooksBO {

	private final WebClient webClient;
	
	public BooksBO(WebClient webClient) {
		this.webClient = webClient;
	}
	
	// 알라딘 OpenAPI : Bestseller
	public List<ItemView> getBestseller() {
		
		// 알라딘 api로 베스트셀러 결과를 받아옴
		Map<String, Object> bestsellerMap = webClient.get()
				.uri("https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkkang565081035001&QueryType=Bestseller&MaxResults=10&start=1&SearchTarget=Book&output=js&Version=20131101&cover=MidBig")
				.retrieve()
				.bodyToMono(Map.class)
				.block();
		
		// 결과의 item List를 ItemView List로 변환 
		List<Map<String, Object>> itemList = (List<Map<String, Object>>) bestsellerMap.get("item");
		List<ItemView> itemViewList = new ArrayList<>();
		for (Map<String, Object> item : itemList) {
			ItemView itemView = new ItemView();
			itemView.setIsbn13((String)item.get("isbn13"));
			
			itemViewList.add(itemView);
		}
		
		
		// ItemView List 반환
		return itemViewList;
	} //-- Bestseller
}
