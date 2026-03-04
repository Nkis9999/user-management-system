package com.course.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String updateProfile(
	        @RequestParam String username,
	        @RequestParam String email,
	        @RequestParam("photo") MultipartFile photo,
	        HttpSession session) throws IOException {

	    String loginUser =
	        (String)session.getAttribute("loginUser");

	    UsersEntity user =
	        usersRepository.findByUsername(loginUser);

	    user.setUsername(username);
	    user.setEmail(email);

	    // 上傳圖片
	    if(photo != null && !photo.isEmpty()){

	        String fileName =
	            photo.getOriginalFilename();

	        String path = "C:\\upload\\";

	        File dir = new File(path);

	        if(!dir.exists()){
	            dir.mkdirs();
	        }

	        photo.transferTo(
	            new File(path + fileName));

	        user.setImgName(fileName);
	    }

	    usersRepository.save(user);

	    session.setAttribute("loginUser",
	            username);

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

	    // 清除登入狀態
	    session.invalidate();

	    // 跳回登入頁並帶成功訊息
	    return "redirect:/login?pwdSuccess=true";
	}
	
	// 刪除帳號
	@PostMapping("/deleteAccount")
	public String deleteAccount(HttpSession session) {
		
		String username = (String)session.getAttribute("loginUser");
		
		UsersEntity user = usersRepository.findByUsername(username);
		
		usersRepository.delete(user);
		
		session.invalidate();
		
		return "redirect:/login?deleteSuccess=true";
	}
	
}
