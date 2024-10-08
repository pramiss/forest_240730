package com.forest.books.bo;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.HtmlUtils;

import com.forest.books.domain.AladinView;
import com.forest.books.domain.ItemView;
import com.forest.like.bo.LikeBO;
import com.forest.like.entity.LikeEntity;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BooksBO {
	
	private final String ttbkey;
	private final LikeBO likeBO;
	private final ProductBO productBO;
	private final WebClient webClient;
	private final ModelMapper modelMapper;
	
	public BooksBO(WebClient webClient, ProductBO productBO, LikeBO likeBO,
			@Value("${aladin.ttbkey}") String ttbkey) {
		this.webClient = webClient;
		this.modelMapper = new ModelMapper();
		this.productBO = productBO;
		this.likeBO = likeBO;
		this.ttbkey = ttbkey;
	}
	
	// 알라딘 : 상품 리스트 API (베스트셀러) 
	public List<ItemView> getBestsellerList(String queryType, String page, int year, int month,int week) {
		// requestUri : Bestseller
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=%s&QueryType=%s&MaxResults=20&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig&Year=%d&Month=%d&Week=%d"
						, ttbkey, queryType, page, year, month, week);
		
		// 알라딘 api로 상품 리스트 결과(key="item", "startIndex")를 받아옴 (AladinView에 존재하는 field만 자동매핑)
		AladinView aladinView = webClient.get()
				.uri(requestUri)
				.retrieve()
				.bodyToMono(AladinView.class)
				.block();
		
		// 검색 결과가 없을 경우 최신 주간 베스트셀러를 가져옴
		if (aladinView.getItem().isEmpty()) {
			requestUri = String.format(
					"http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=%s&QueryType=%s&MaxResults=20&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig"
					, ttbkey, queryType, page);
			
			aladinView = webClient.get()
					.uri(requestUri)
					.retrieve()
					.bodyToMono(AladinView.class)
					.block();
		}
		
		// aladinView의 item의 각각 요소들 -> itemViewList에 매핑
		List<ItemView> itemViewList = modelMapper.map(aladinView.getItem(), new TypeToken<List<ItemView>>() {}.getType());

		// itemViewList 가공
		for (ItemView item : itemViewList) {
						
			// 1. title 가공
			String title = item.getTitle();
			title = HtmlUtils.htmlUnescape(title.split("-")[0]);
			item.setTitle(title);
			
			// 2. author 가공
			String author = item.getAuthor();
			author = HtmlUtils.htmlUnescape(author.split("\\(")[0]);
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
			
			// 2) 해당하는 List<product>가 있는지 찾기
			List<ProductEntity> productList = productBO.getProductListByIsbn(item.getIsbn13());
			item.setProductList(productList);
		}

		// ItemView의 queryDate
		year = Integer.parseInt(aladinView.getQuery().split(";")[2].replace("Year=", ""));
		month = Integer.parseInt(aladinView.getQuery().split(";")[3].replace("Month=", ""));
		week = Integer.parseInt(aladinView.getQuery().split(";")[4].replace("Week=", ""));
		ItemView.setQueryDate(year, month, week);
		
        return itemViewList;
	} //-- 상품 리스트 API (베스트셀러)
	
	
	/**
	 * 알라딘 : 상품 리스트 API (신간)
	 * @param queryType
	 * @param page
	 * @return
	 */
	public List<ItemView> getItemNewList(String queryType, String page) {
		// requestUri : ItemNewSpecial ItemNewAll
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemList.aspx?ttbkey=%s&QueryType=%s&MaxResults=20&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig"
						, ttbkey, queryType, page);
		
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
			title = HtmlUtils.htmlUnescape(title.split("-")[0]);
			item.setTitle(title);
			
			// 2. author 가공
			String author = item.getAuthor();
			author = HtmlUtils.htmlUnescape(author.split("\\(")[0]);
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
			
			// 2) 해당하는 List<product>가 있는지 찾기
			List<ProductEntity> productList = productBO.getProductListByIsbn(item.getIsbn13());
			item.setProductList(productList);
		}
		
        return itemViewList;
	} //-- 상품 리스트 API (신간)
	
	/**
	 * 알라딘 : 상품 검색 API (검색결과)
	 * @param query
	 * @param queryType
	 * @param page
	 * @return
	 */
	public List<ItemView> getItemSearch(String query, String queryType, String page) {
		// requestUri : 10 item 씩 가져옴
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=%s&Query=%s&QueryType=%s&MaxResults=10&start=%s&SearchTarget=Book&output=js&Version=20131101&cover=MidBig"
						, ttbkey, query, queryType, page);

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
			title = HtmlUtils.htmlUnescape(title.split("-")[0]);
			item.setTitle(title);
			
			// 2. author 가공
			String author = item.getAuthor();
			author = HtmlUtils.htmlUnescape(author.split("\\(")[0]);
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
			
			// 2) 해당하는 List<product>가 있는지 찾기
			List<ProductEntity> productList = productBO.getProductListByIsbn(item.getIsbn13());
			item.setProductList(productList);
		}
		
        return itemViewList;
	} //-- 상품 검색 API
	
	/**
	 * 알라딘 : 상품 조회 API (단건), 결과: ItemView or null
	 * @param itemId
	 * @param itemIdType
	 * @return
	 */
	public ItemView getItemLookUp(String itemId, String itemIdType) { // isbn, itemIdType=ISBN13(고정)
		String requestUri = String.format(
						"http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=%s&itemIdType=%s&ItemId=%s&output=js&Version=20131101&Cover=Big"
						, ttbkey, itemIdType, itemId);

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
		
		// 1. aladinView의 item의 각각 요소들 -> itemViewList에 매핑
		ItemView itemView = modelMapper.map(aladinView.getItem().get(0), ItemView.class);

		// (customerReviewRank 가공)
		double customerReviewRank = itemView.getCustomerReviewRank();
		customerReviewRank /= 2;
		itemView.setCustomerReviewRank(customerReviewRank);
		
		// (title 가공)
		String title = HtmlUtils.htmlUnescape(itemView.getTitle()); // entity to String
		itemView.setTitle(title);
		
		// (author 가공)
		String author = HtmlUtils.htmlUnescape(itemView.getAuthor()); // entity to String
		itemView.setAuthor(author);
		
		// (description 가공)
		String description = HtmlUtils.htmlUnescape(itemView.getDescription()); // entity to String
		itemView.setDescription(description);
		
		// 2. itemView에 List<productEntity>를 담는다.
		List<ProductEntity> productList = productBO.getProductListByIsbn(itemView.getIsbn13());
		itemView.setProductList(productList);
		
		return itemView;
	} //-- 상품 조회 API
	
	// 유저의 좋아요 리스트를 가져옴 (여러건, 리스트)
	public List<LikeEntity> getLikeListByUserId(int userId) {
		return likeBO.getLikeListByUserId(userId);
	} //-- 유저의 좋아요 리스트를 가져옴
	
	// 유저, 책의 좋아요 여부 확인 (단건, boolean)
	public boolean isLikeById(int userId, String isbn) {
		return likeBO.isLikeById(userId, isbn);
	}
}
