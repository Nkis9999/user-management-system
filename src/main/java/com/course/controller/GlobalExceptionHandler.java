package com.course.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String handleMaxSizeException(Model model) {
		
		model.addAttribute("errorMsg" , "圖片不能超過 2MB");
		
		return "profile";
	}
	
}
