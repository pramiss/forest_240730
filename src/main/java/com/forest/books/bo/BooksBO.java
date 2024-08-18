package com.forest.books.bo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.HtmlUtils;

import com.forest.books.domain.AladinView;
import com.forest.books.domain.ItemView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BooksBO {

	private final WebClient webClient;
	private final ModelMapper modelMapper;
	
	public BooksBO(WebClient webClient) {
		this.webClient = webClient;
		this.modelMapper = new ModelMapper();
	}
	
	// 알라딘 : 상품 리스트 API
	public List<ItemView> getItemList(String queryType, String page) {
		// requestUri : Bestseller ItemNewSpecial ItemNewAll
		String requestUri = String.format(
						"https://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=ttbkkang565081035001&QueryType=%s&MaxResults=20&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig"
						, queryType, page);
		
		// 알라딘 api로 상품 리스트 결과(key="item", "startIndex")를 받아옴 (AladinView에 존재하는 field만 자동매핑)
		AladinView aladinView = webClient.get()
				.uri(requestUri)
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
			
			// 4. description 가공
			String description = item.getDescription();
			description = HtmlUtils.htmlUnescape(description); // entity to String
			if (description.length() > 130) {
				description = description.substring(0, 100) + "...";
			}
			item.setDescription(description);
		}
		
        return itemViewList;
	} //-- 상품 리스트 API
	
	// 알라딘 : 상품 검색 API
	public List<ItemView> getItemSearch(String query, String queryType, String page) {
		// requestUri : 10 item 씩 가져옴
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbkkang565081035001&Query=%s&QueryType=%s&MaxResults=10&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig"
						,query, queryType, page);

		log.info("****** API uri : " + requestUri);
		
		// 알라딘 api로 상품 리스트 결과(key="item", "startIndex")를 받아옴 (AladinView에 존재하는 field만 자동매핑)
		AladinView aladinView = webClient.get()
				.uri(requestUri)
				.retrieve()
				.bodyToMono(AladinView.class)
				.block();
		
		// aladinView의 item의 각각 요소들 -> itemViewList에 매핑
		List<ItemView> itemViewList = modelMapper.map(aladinView.getItem(), new TypeToken<List<ItemView>>() {}.getType());
		ItemView.setTotalResults(aladinView.getTotalResults());
		
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
			
			// 4. description 가공
			String description = item.getDescription();
			description = HtmlUtils.htmlUnescape(description); // entity to String
			if (description.length() > 130) {
				description = description.substring(0, 100) + "...";
			}
			item.setDescription(description);
		}
		
        return itemViewList;
	} //-- 상품 검색 API
	
	// 알라딘 : 상품 조회 API, 결과: ItemView or null
	public ItemView getItemLookUp(String itemId, String itemIdType) { // isbn, itemIdType=ISBN13(고정)
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbkkang565081035001&itemIdType=%s&ItemId=%s&output=js&Version=20131101&Cover=Big"
						, itemIdType, itemId);

		log.info("****** API uri : " + requestUri);
		
		// 알라딘 api로 상품 리스트 결과(key="item", "startIndex")를 받아옴 (AladinView에 존재하는 field만 자동매핑)
		AladinView aladinView = webClient.get()
				.uri(requestUri)
				.retrieve()
				.bodyToMono(AladinView.class)
				.block();
		
		// 해당 상품이 없는 경우
		if (aladinView.getItem() == null) {
			return null;
		}
		
		// aladinView의 item의 각각 요소들 -> itemViewList에 매핑
		ItemView itemView = modelMapper.map(aladinView.getItem().get(0), ItemView.class);

		return itemView;
	} //-- 상품 조회 API
}
