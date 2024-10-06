package com.forest.kakao.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forest.user.bo.UserBO;
import com.forest.user.domain.User;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaoService {

    private String clientId;
    private String clientSecret;
    private final String KAUTH_TOKEN_URL_HOST;
    private final String KAUTH_USER_URL_HOST;
    private final UserBO userBO;
    

    public KakaoService(@Value("${kakao.client_id}") String clientId,
    		@Value("${kakao.client_secret}") String clientSecret,
    		UserBO userBO) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
        KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
        this.userBO = userBO;
    }

    // accessToken 받기
    public String getAccessTokenFromKakao(String code) {
    	
    	String response = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(String.class)
                .block();

    	ObjectMapper objectMapper = new ObjectMapper();
    	String accessToken = "";
    	
		try {
			Map<String, Object> responseMap;
			responseMap = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
			accessToken = (String) responseMap.get("access_token");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
        return accessToken;
    }
    
    // 사용자 정보 받기
    public Map<String, Object> getUserInfo(String accessToken) {
    	
        String userInfoString = WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(String.class)
                .block();

    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> userInfo = null;
    	
		try {
			userInfo = objectMapper.readValue(userInfoString, new TypeReference<Map<String, Object>>() {});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        return userInfo;
    }
    
    // 카카오 사용자 조회
    public User getUserByKakaoId(String kakaoId) {
    	return userBO.getUserByKakaoId(kakaoId);
    }
}