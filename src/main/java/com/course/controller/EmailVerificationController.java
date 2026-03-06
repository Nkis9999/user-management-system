package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;
import com.course.service.LoginService;

@Controller
public class EmailVerificationController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token , Model model) {

        boolean success = loginService.verifyEmail(token);

        if(!success){
            return "verify-fail";
        }

        return "redirect:/login?verified";
    }

    // 重新寄信頁面
    @GetMapping("/resend")
    public String resendPage(){
        return "resend";
    }

    // 重新寄信
    @PostMapping("/resend")
    public String resendEmail(@RequestParam String email , Model model){

        UsersEntity user = usersRepository.findByEmail(email);

        // 如果帳號不存在
        if(user == null){
            model.addAttribute("error","此 Email 不存在");
            return "resend";
        }

        // 如果已經驗證
        if(user.getVerified()){
            model.addAttribute("error","此 Email 已驗證");
            return "resend";
        }

        loginService.resendVerificationEmail(email);

        return "resend-success";
    }

}