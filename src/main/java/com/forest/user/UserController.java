package com.forest.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forest.books.domain.ItemView;
import com.forest.order.domain.OrderView;
import com.forest.product.entity.ProductView;
import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
public class UserController {

	private final UserBO userBO;
	
	public UserController(UserBO userBO) {
	    this.userBO = userBO;
	} 
	
	/**
	 * 회원정보상세 페이지
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/info")
	public String userInfo(HttpSession session, Model model) {
		Integer userId = (Integer)session.getAttribute("userId");

		// 유저 아이디로 user를 가져온 후 model에 담기
		User user = userBO.getUserById(userId);
		model.addAttribute("user", user);
		
		return "user/info";
	} //-- 회원상세 페이지
	
	/**
	 * 좋아요 목록 페이지
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/likes")
	public String userLikes(HttpSession session, Model model) {
		
		// 현재 세션 유저의 좋아요 목록을 전부 불러옴 (최종적으로 필요한 것 : isbn 리스트)
		int userId = (int) session.getAttribute("userId");
		List<String> isbnList = userBO.getLikeListByUserId(userId).stream()
											.map(likeEntity -> likeEntity.getId().getIsbn())
											.collect(Collectors.toList());

		
		// ItemView에 isbn에 해당하는 도서 정보를 담는다 (알라딘 상품 조회 API (단건) 이용, 최종결과: List<ItemView>)
		List<ItemView> itemViewList = isbnList.stream()
											.map(isbn -> userBO.getItemLookUp(isbn))
											.collect(Collectors.toList());
		
		// List<ItemView>를 Model에 담는다.
		model.addAttribute("itemList", itemViewList);
		
		// 좋아요 목록 페이지로 이동
		return "user/likes";
	} //-- 좋아요 목록 페이지
	
    /**
     * 장바구니 페이지
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/cart")
    public String userCart(HttpSession session, Model model) {
        
        // 현재 세션 유저의 장바구니를 불러옴 (최종적으로 필요한 것 : productId 리스트)
        int userId = (int) session.getAttribute("userId");
        List<Integer> productIdList = userBO.getCartListByUserId(userId).stream()
                                            .map(cartEntity -> cartEntity.getCartId().getProductId())
                                            .collect(Collectors.toList());
        
        // productId 리스트 -> ProductView 리스트
        List<ProductView> ProductViewList = productIdList.stream()
                                            .map(productId -> userBO.getProductViewByProductId(productId))
                                            .collect(Collectors.toList());
        
        // List<ItemView>를 Model에 담는다.
        model.addAttribute("ProductViewList", ProductViewList);

        // 장바구니 목록 페이지로 이동
        return "user/cart";
    }
	
	/**
	 * 주문목록 페이지
	 * @param session
	 * @param model
	 * @return
	 */
    @GetMapping("/orders")
    public String userOrders(HttpSession session, Model model) {
    	
    	// 유저의 주문 목록(List<OrderView>)를 가지고 온다.
    	List<OrderView> orderViewList = userBO.getOrderViewListByUserId((Integer)session.getAttribute("userId"));
    	model.addAttribute("orderViewList", orderViewList);
    	
    	return "user/orders";
    } //-- 주문목록 페이지
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		
		// 로그아웃
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// 리다이렉트
		return "redirect:/";
	} //-- 로그아웃API
}
