package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.course.entity.UsersEntity;
import com.course.model.UserVo;
import com.course.repository.UsersRepository;
import com.course.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 首頁轉導到 login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // 顯示登入頁
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 處理登入
    @PostMapping("/doLogin")
    public String login(UserVo userVo , HttpSession session){

        boolean result =
            loginService.checkLogin(userVo);

        if(result){
        	
        	session.setAttribute("loginUser" , userVo.getUsername());
            
        	session.setAttribute("loginTime", System.currentTimeMillis());
        	
        	return "loginSuccess";
        }

        return "redirect:/login?error";
    }
    
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
    	return "loginSuccess";
    }

    // 忘記密碼
    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
    	return "forgotPassword";
    }
    
    @PostMapping("/forgotPassword")
    public String forgotPassword(String username , Model model) {
    	
    	UsersEntity user = usersRepository.findByUsername(username);
    	
    	if(user == null) {
    		
    		model.addAttribute("error" , "找不到此帳號");
    		
    		return "forgotPassword";
    	}
    		
    		model.addAttribute("username" , username);
    	
    	return "resetPassword";
    }
    
    @PostMapping("/resetPassword")
    public String resetPassword(
    		String username,
    		String newPassword,
    		String confirmPassword,
    		Model model) {
    	
    	UsersEntity user = usersRepository.findByUsername(username);
    	
    	if(!newPassword.equals(confirmPassword)) {
    		
    		model.addAttribute("error" , "兩次密碼不一致");
    		
    		model.addAttribute("username" , username);
    		
    		return "resetPassword";
    		
    	}
    	
    	 user.setPassword(passwordEncoder.encode(newPassword));

    	
    		usersRepository.save(user);
    		
    	return "redirect:/login?resetSuccess=true";
    }
    
    
    // 顯示註冊頁
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 處理註冊
    @PostMapping("/register")
    public String register(@ModelAttribute UserVo userVo,Model model) {

        boolean isRegister = loginService.registerUser(userVo);

        if (isRegister) {
            return "redirect:/login?Success";
        } else {
        	
        	model.addAttribute("error","此帳號已註冊過");
            return "register";
        }
    }
    
    // 更新資料功能
    public String updateProfile(UserVo userVo , HttpSession session) {
    	
    	String username = (String)session.getAttribute("loginUser");
    	
    	loginService.updateUser(username , userVo);
    	
    	return "redirect:/profile";
    }
}