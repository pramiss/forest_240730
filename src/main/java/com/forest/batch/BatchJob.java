package com.forest.batch;

import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.forest.order.bo.OrderBO;
import com.forest.order.domain.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Component
public class BatchJob {

	private final OrderBO orderBO;
	
	public BatchJob(OrderBO orderBO) {
		this.orderBO = orderBO;
	}
	
	// 2일마다 "배송중"인 주문을 "배송완료"로 바꾸기
	@Scheduled(cron = "0 0 0 */2 * *")
	public void orderStatusTask() {
		
		// 1. order 중 "배송중" 인 order들을 모두 가지고 온다. (order id가 필요)
		List<Order> orderList = orderBO.getOrderListByStatus("배송중");
		
		if (orderList.isEmpty()) { // 현재 배송중인 상품이 없는 경우
			log.info("***** 배송중인 상품이 없습니다 *****");
			return;
		}
		
		// 2. 해당 주문(order id)의 배송 상태를 "배송완료"로 변경한다.
		for (Order order : orderList) {
			int orderId = order.getId();
			orderBO.updateOrderStatus(orderId, "배송완료");
			log.info("***** 배송중 -> 배송완료, order id : {} *****", orderId);
		}
	}
}
