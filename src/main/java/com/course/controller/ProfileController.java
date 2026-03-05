package com.course.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;

@Controller
public class ProfileController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 個人資料頁
    @GetMapping("/profile")
    public String profile(Authentication authentication,
                          Model model){

        String username =
                authentication.getName();

        UsersEntity user =
                usersRepository.findByUsername(username);
        
        model.addAttribute("user", user);

        return "profile";
    }

    // 更新資料
    @PostMapping("/updateProfile")
    public String updateProfile(
            Authentication authentication,
            @RequestParam String email,
            @RequestParam("photo") MultipartFile photo)
            throws IOException {

        String username = authentication.getName();

        UsersEntity user =
                usersRepository.findByUsername(username);

        user.setEmail(email);

        // 上傳圖片
        if(photo != null && !photo.isEmpty()){
        	
        	// 檢查檔案大小(2MB)
        	if(photo.getSize() > 2 * 1024 * 1024) {
        		
        		return "redirect:/profile?error=size";
        	}
        	
        	// 檢查檔案格式
        	String contentType = photo.getContentType();
        	String fileName2 = photo.getOriginalFilename().toLowerCase();
        	
        	if(contentType == null ||
        	  (!contentType.equals("image/jpeg") &&
		       !contentType.equals("image/png") &&
		       !contentType.equals("image/jpg"))){
        		  
        		return "redirect:/profile?error=format";
        	  }
        	if(!(fileName2.endsWith(".jpg") ||
    		     fileName2.endsWith(".jpeg") ||
    		     fileName2.endsWith(".png"))) {

        		    return "redirect:/profile?error=format";}
        	
            String path = "C:\\upload\\";

            File dir = new File(path);

            if(!dir.exists()){
                dir.mkdirs();
            }
            
            // 刪除舊照片
            if(user.getImgName() != null && !user.getImgName().equals("default-avatar.png")) {
            	
            	File oldFile = new File(path + user.getImgName());
            	
            	if(oldFile.exists()) {
            		oldFile.delete();
            	}
            }
            
            // 產生新檔名
            String fileName =
                    System.currentTimeMillis() + "_"
                    + photo.getOriginalFilename();

            File dest =
                    new File(path + File.separator + fileName);

            photo.transferTo(dest);
            
            user.setImgName(fileName); // DB 更新
        }

        usersRepository.save(user);

        return "redirect:/profile?success=true";
    }

    // 修改密碼
    @PostMapping("/changePassword")
    public String changePassword(
            Authentication authentication,
            String oldPassword,
            String newPassword,
            String confirmPassword,
            Model model) {

        String username =
                authentication.getName();

        UsersEntity user =
                usersRepository.findByUsername(username);

        // 驗證舊密碼
        if(!passwordEncoder.matches(
                oldPassword,
                user.getPassword())){

            model.addAttribute("error",
                    "舊密碼錯誤!");

            model.addAttribute("user", user);

            return "profile";
        }

        // 驗證新密碼
        if(!newPassword.equals(confirmPassword)){

            model.addAttribute("error",
                    "兩次密碼不一致!");

            model.addAttribute("user", user);

            return "profile";
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        usersRepository.save(user);

        return "redirect:/login?pwdSuccess=true";
    }

    // 刪除帳號
    @PostMapping("/deleteAccount")
    public String deleteAccount(Authentication authentication) {

        String username =
                authentication.getName();

        UsersEntity user =
                usersRepository.findByUsername(username);
        
        String path = "C:\\upload\\";
        
        // 刪除頭像
        if(user.getImgName() != null && 
        		!user.getImgName().equals("default-avatar.png")) {
        	
        	File file = new File(path + user.getImgName());
        	
        	if(file.exists()) {
        		file.delete();
        	}
        }
        
        // 刪除帳號
        usersRepository.delete(user);

        return "redirect:/login?deleteSuccess=true";
    }
}