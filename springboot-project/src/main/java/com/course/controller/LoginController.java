package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
            return "loginSuccess";
        }

        return "redirect:/login?error";
    }
    
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
    	return "loginSuccess";
    }
//    @PostMapping("/login")
//    public String loginAction(@ModelAttribute UserVo userVo) {
//    	
//    	boolean isLogin = loginService.checkLogin(userVo);
//    	
//    	if(isLogin) {
//    		return "redirect:/users";
//    	}else {
//    		return "redirect:/login";
//    	}
//    }

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
    
    // 建立個人資料頁
//    @GetMapping("/profile")
//    public String profile(HttpSession session , Model model) {
//    	
//    	String username = (String)session.getAttribute("loginUser");
//    	
//    	UsersEntity user = usersRepository.findByUsername(username);
//    	
//    	model.addAttribute("user" , user);
//    	
//    	return "profile";
//    	
//    }
    
    // 更新資料功能
    public String updateProfile(UserVo userVo , HttpSession session) {
    	
    	String username = (String)session.getAttribute("loginUser");
    	
    	loginService.updateUser(username , userVo);
    	
    	return "redirect:/profile";
    }
}