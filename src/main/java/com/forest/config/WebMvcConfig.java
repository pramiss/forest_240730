package com.forest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.forest.interceptor.PermissionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final PermissionInterceptor permissionInterceptor;
	
	public WebMvcConfig(PermissionInterceptor permissionInterceptor) {
		this.permissionInterceptor = permissionInterceptor;
	}
	
	// Permission 인터셉터
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(permissionInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/error", "/css/**", "/img/**");
	}
}
