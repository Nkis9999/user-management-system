package com.course.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    // 1. 處理手動上傳的（C槽）
	    registry.addResourceHandler("/upload/**")
	            .addResourceLocations("file:C:/upload/");

	    // 2. 處理預設圖片（專案內的 static 資料夾）
	    registry.addResourceHandler("/images/**")
	            .addResourceLocations("classpath:/static/images/");
	}
}
