package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@PostMapping("/changePassword")
	public String changePassword(
			String oldPassword, 
			String newPassword,
			String confirmPassword,
			HttpSession session,
			Model model) {
		
		String username = (String)session.getAttribute("loginUser");
		
		UsersEntity user = usersRepository.findByUsername(username);
		
		// 驗證舊密碼
	    if(!passwordEncoder.matches(
	            oldPassword,
	            user.getPassword())){

	        model.addAttribute("error",
	                "舊密碼錯誤!");

	        model.addAttribute("user", user);

	        return "profile";
	    }

	    // 驗證新密碼一致
	    if(!newPassword.equals(confirmPassword)){

	        model.addAttribute("error",
	                "兩次密碼不一致!");

	        model.addAttribute("user", user);

	        return "profile";
	    }

	    // 更新密碼
	    user.setPassword(
	        passwordEncoder.encode(newPassword));

	    usersRepository.save(user);

	    model.addAttribute("success",
	            "密碼修改成功!");

	    model.addAttribute("user", user);

	    return "profile";
	}
	
}
