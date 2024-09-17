package com.forest.kakao;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forest.kakao.service.KakaoService;
import com.forest.user.domain.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class KakaoLoginRestController {
	
	private final KakaoService kakaoService;
	
	public KakaoLoginRestController(KakaoService kakaoService) {
		this.kakaoService = kakaoService;
	}
	
    @GetMapping("/callback/kakao")
    public void callbackKakao(@RequestParam("code") String code,
    		HttpServletResponse response,
    		HttpSession session) throws IOException {
    	
    	// 1. Access Token을 가져온다.
    	String accessToken = kakaoService.getAccessTokenFromKakao(code);
    	log.info("***** [Response Token] : {}", accessToken);
    	
    	// 2. User Info를 가져온다.
    	Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
    	log.info("***** [User Info] : {}", userInfo);
    	
    	// 3. User의 kakaoId를 가지고 온다. (by id)
    	Long kakaoIdLong = (Long) userInfo.get("id");
    	String kakaoId = kakaoIdLong != null ? kakaoIdLong.toString() : null;
    	log.info("***** [kakaoId] : {}", kakaoId);
    	
    	// User DB에 해당하는 Kakao User가 있는지 확인한다.
    	User user = kakaoService.getUserByKakaoId(kakaoId);
    	
    	// 가입X -> 회원가입 페이지, 가입O -> 로그인
    	if (user == null) {
    		// kakaoId, userName을 기본 정보로 넘겨준다.
            Map<String, Object> properties = (Map<String, Object>) userInfo.get("properties");
            String nickname = properties != null ? (String) properties.get("nickname") : "";            

            // 카카오 회원가입 페이지로 리다이렉트
            String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8.toString());
            String redirectUrl = "/auth/join/kakao?kakaoId=" + kakaoId + "&nickname=" + encodedNickname;
            response.sendRedirect(redirectUrl);
    	} else {
    		// user를 카카오 로그인을 진행한다.
    		session.setAttribute("userId", user.getId());
    		session.setAttribute("userAuthType", user.getAuthType());
    		session.setAttribute("userKakaoId", user.getKakaoId());
    		session.setAttribute("userName", user.getName());
    		session.setAttribute("userAddress", user.getAddress());
    		session.setAttribute("userPhoneNumber", user.getPhoneNumber());
    		session.setAttribute("userEmail", user.getEmail());
    		
    		response.sendRedirect("/");
    	}
    	
    }
}
