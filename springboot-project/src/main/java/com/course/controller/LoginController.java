package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.course.model.UserVo;
import com.course.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

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
    public String login(UserVo userVo){

        boolean result =
            loginService.checkLogin(userVo);

        if(result){
            return "loginSuccess";
        }

        return "redirect:/login?error";
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
}