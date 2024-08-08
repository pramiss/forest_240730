package com.forest.books.bo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.forest.books.domain.AladinView;
import com.forest.books.domain.ItemView;

@Service
public class BooksBO {

	private final WebClient webClient;
	private final ModelMapper modelMapper;
	
	public BooksBO(WebClient webClient) {
		this.webClient = webClient;
		this.modelMapper = new ModelMapper();
	}
	
	// 알라딘 OpenAPI : Bestseller
	public List<ItemView> getBestseller() {
		
		// 알라딘 api로 베스트셀러 결과(key="item"만)를 받아옴
		AladinView aladinView = webClient.get()
				.uri("https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkkang565081035001&QueryType=Bestseller&MaxResults=20&start=1&SearchTarget=Book&output=js&Version=20131101&cover=MidBig")
				.retrieve()
				.bodyToMono(AladinView.class)
				.block();
		
		// aladinView의 item의 각각 요소들 -> itemViewList에 매핑
		List<ItemView> itemViewList = modelMapper.map(aladinView.getItem(), new TypeToken<List<ItemView>>() {}.getType());

		// itemViewList 가공
		for (ItemView item : itemViewList) {
			// 1. title 가공
			String title = item.getTitle();
			title = title.split("-")[0];
			item.setTitle(title);
			
			// 2. author 가공
			String author = item.getAuthor();
			author = author.split("\\(")[0];
			item.setAuthor(author);
			
			// 3. customerReviewRank 가공
			double customerReviewRank = item.getCustomerReviewRank();
			customerReviewRank /= 2;
			item.setCustomerReviewRank(customerReviewRank);
		}
		
		
        return itemViewList;
		
	} //-- Bestseller
}
