package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	
	@Autowired
	UsersRepository usersRepository;
	
	@GetMapping("/profile")
	public String profile(HttpSession session,
	                      Model model){

	    String username =
	        (String) session.getAttribute("loginUser");

	    System.out.println("Session username=" + username);

	    UsersEntity user =
	        usersRepository.findByUsername(username);

	    System.out.println("DB user=" + user);

	    model.addAttribute("user", user);

	    return "profile";
	}
	
//	@GetMapping("/profile")
//	public String profile(HttpSession session ,Model model){
//		
//		String username = (String)session.getAttribute("loginUser");
//		
//		if(username == null) {
//			return "redirect:/login";
//		}
//		
//		UsersEntity user = usersRepository.findByUsername(username);
//		
//		model.addAttribute("username",username);
//		
//		return "profile";
//	}
	
}
