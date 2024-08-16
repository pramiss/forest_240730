package com.forest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.forest.common.FileManagerService;
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
	} //-- Permission 인터셉터
	
	// 이미지 path와 서버에 업로드 된 실제 이미지와 매핑 설정
	@Override
	public void addResourceHandlers(	
			ResourceHandlerRegistry registry) {
		
		registry 
		.addResourceHandler("/images/**") // web path => http://localhost/images/aaaa_1721302413668/castle.jpg 
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 파일 경로 => "file:///" MAC: // , WINDOW: ///
	}
}
