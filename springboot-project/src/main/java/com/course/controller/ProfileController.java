package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@PostMapping("/updateProfile")
	public String updateProfile(@RequestParam String username ,@RequestParam String email ,HttpSession session) {
		
		// 取得目前登入者
		String loginUser = (String)session.getAttribute("loginUser");
		
		UsersEntity user = usersRepository.findByUsername(loginUser);
		
		// 更新資料
		user.setUsername(username);
		user.setEmail(email);
		
		usersRepository.save(user);
		
		// 更新 session
		session.setAttribute("loginUser", username);
		return "redirect:/profile?success=true";
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
