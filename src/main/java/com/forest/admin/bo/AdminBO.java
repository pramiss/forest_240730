package com.forest.admin.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forest.book.bo.BookBO;
import com.forest.books.bo.BooksBO;
import com.forest.books.domain.ItemView;
import com.forest.order.bo.OrderBO;
import com.forest.order.bo.OrderProductBO;
import com.forest.order.domain.Order;
import com.forest.order.domain.OrderProduct;
import com.forest.order.domain.OrderView;
import com.forest.product.bo.ProductBO;
import com.forest.product.entity.ProductEntity;
import com.forest.product.entity.ProductView;

@Service
public class AdminBO {

	private OrderBO orderBO;
	private OrderProductBO orderProductBO;
	private BooksBO booksBO;
	private ProductBO productBO;
	private BookBO bookBO;
	
	public AdminBO(BooksBO booksBO, ProductBO productBO, BookBO bookBO,
			OrderBO orderBO, OrderProductBO orderProductBO) {
		this.booksBO = booksBO;
		this.productBO = productBO;
		this.bookBO = bookBO;
		this.orderBO = orderBO;
		this.orderProductBO = orderProductBO;
	}
	
	/**
	 * 도서 정보 API
	 * @param itemId
	 * @param itemIdType
	 * @return
	 */
	public ItemView getItemView(String itemId, String itemIdType) {
		return booksBO.getItemLookUp(itemId, itemIdType);
	} //-- 도서 정보 API
	
	/**
	 * 도서&상품 추가 API
	 * @param product
	 * @param book
	 */
	public void addProductAndBook(
			Map<String, Object> product, Map<String, Object> book, MultipartFile file) {
		
		// 상품 추가
		productBO.addProduct(product, file); 
		
		// 도서 추가
		bookBO.addBook(book);
	} //-- 도서&상품 추가 API
	
	/**
	 * 상품 리스트 가져오기
	 * @return
	 */
	public List<ProductEntity> getProductList() {
		return productBO.getProductList();
	} //-- 상품 리스트 가져오기
	
	/**
	 * 상품 업데이트
	 * @param saleStatus
	 */
	public void updateProduct(Map<String, String> saleStatus) {
		productBO.updateProduct(saleStatus);
	} //-- 상품 판매상태 업데이트
	
	// 주문 업데이트
	public void updateOrderStatus(int orderId, String status) {
		orderBO.updateOrderStatus(orderId, status);
	}
	
	// 상품(도서) 삭제
	public void deleteProduct(List<String> productIdList) {
		
		// 리스트 반복
		for (String productIdString : productIdList) {
			int productId = Integer.parseInt(productIdString);
			
			// 1. 해당 상품 삭제 후 isbn 받아오기
			String isbn = productBO.deleteProduct(productId);
			
			if (isbn.equals("false")) { // 삭제할 상품 없음
				return;
			}
			
			// 2. 해당 isbn 상품들이 또 있는지 조회
			List<ProductEntity> productList = productBO.getProductListByIsbn(isbn);
			
			if (productList.isEmpty()) {
				// 3. 없다면 book 삭제
				bookBO.deleteBookById(isbn);
			}
		}
	
	} //-- 상품(도서) 삭제
	
	// 모든 OrderView 가져오기
	public List<OrderView> getOrderViewList() {
		
		List<OrderView> orderViewList = new ArrayList<>();
		
		// 1. 전체 order 가져오기
		List<Order> orderList = orderBO.getOrderListByIdDesc();
		
		// 2. order에 해당하는 orderProuct들 가져오기
		for (Order order : orderList) {
			
			// order에 해당하는 orderProduct를 가져온다.
			OrderView orderView = new OrderView();
			List<OrderProduct> orderProductList = orderProductBO.getOrderProductListByPaymentId(order.getPaymentId());
			
			// productIdTitle
			Map<Integer, String> productIdTitle = new HashMap<>();
			
			// orderProductList(productId) -> productIdTitle(productId:title)
			for (OrderProduct orderProduct : orderProductList) {
				int productId = orderProduct.getProductId();
				
				// productId에 해당하는 title을 가져온다.
				ProductView productView = productBO.getProductView(productId);
				String title = productView.getBook().getTitle();
				
				// productIdTitle에 넣는다.
				productIdTitle.put(productId, title);
			}
			
			orderView.setOrder(order);
			orderView.setProductIdName(productIdTitle);
			orderViewList.add(orderView);
		}
		
		return orderViewList;
	}
}
