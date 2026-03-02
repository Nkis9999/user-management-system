package com.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.course.entity.UsersEntity;
import com.course.model.UserEntity;
import com.course.repository.UsersRepository;
import com.course.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UsersRepository usersRepository;

    @GetMapping("/users")
    public String users(
            Model model,
            @RequestParam(defaultValue="0")
            int page){

        Page<UsersEntity> userPage =
        userService.getUsers(page);

        model.addAttribute("users",
                userPage.getContent());

        model.addAttribute("total",
                usersRepository.count());

        model.addAttribute("page",
                page);

        return "users";
    }

    @GetMapping("/search")
    public String search(String keyword , Model model) {
    	
    	Page<UserEntity> userPage = userService.searchUsers(keyword, 0);
    	
    	model.addAttribute("users" , userPage.getContent());
    	
    	return "users";
    }
    
    
    
}