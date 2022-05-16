package com.project.experience;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 저장된 이미지 파일의 경로를 저장
		registry
			.addResourceHandler("/diary_file/**")
			.addResourceLocations("file:///C:/Spring/spring-workspace/expreince/expreince/src/main/resources/static/diary_file/");
		
	}
	
	
}
