package com.forest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebClientConfig {
	
	// webClient Bean 등록
	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl("http://localhost")
				.build();
	}
}
