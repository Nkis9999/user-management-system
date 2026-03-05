package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;
import com.course.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/users")
    public String users(
            Model model,
            HttpSession session,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(required=false) String keyword){
    	
        Page<UsersEntity> userPage;

        if(keyword == null || keyword.isEmpty()){

            userPage =
                userService.getUsers(page);

        }else{

            userPage =
                userService.searchUsers(keyword,page);
        }

        // 限制 page 不超過最大頁數
        if(page >= userPage.getTotalPages()) {
        	page = userPage.getTotalPages() - 1;
        	
        	if(page < 0) {
        		page = 0;
        	}
        	
        	if(keyword == null || keyword.isEmpty()) {
        		userPage = userService.getUsers(page);
        	}else {
        		userPage = userService.searchUsers(keyword, page);
        	}
        }
        
        model.addAttribute("users",
                userPage.getContent());

        model.addAttribute("total",
                usersRepository.count());

        model.addAttribute("page",
                page);

        model.addAttribute("keyword",
                keyword);
        
        model.addAttribute("totalPages" , userPage.getTotalPages());

        return "users";
    }

}