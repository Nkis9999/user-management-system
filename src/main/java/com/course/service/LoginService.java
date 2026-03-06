package com.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.course.entity.UsersEntity;
import com.course.model.UserVo;
import com.course.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	//---- 登入
	public boolean checkLogin(UserVo userVo) {

	    System.out.println("username="
	            + userVo.getUsername());

	    System.out.println("password="
	            + userVo.getPassword());

	    UsersEntity user =
	        usersRepository.findByUsername(
	                userVo.getUsername());

	    if(user == null) {
	        System.out.println("查無帳號");
	        return false;
	    }

	    System.out.println("DB password="
	            + user.getPassword());

	    boolean result =
	        passwordEncoder.matches(
	            userVo.getPassword(),
	            user.getPassword());

	    System.out.println("matches="+result);
	    
	    // 密碼正確後檢查 Email 是否驗證
	    if(result && !user.getVerified()) {
	    	System.out.println("Email 尚未驗證");
	    	return false;
	    }

	    return result;
	}
	
	// 註冊
	public boolean registerUser(UserVo userVo) {

		// 以 Username 去檢查
	    UsersEntity existUser = usersRepository.findByUsername(userVo.getUsername());
	    
	    if(existUser != null) {
	    	return false; //帳號已存在
	    }
	    
	    UsersEntity user = new UsersEntity();
	    
	    user.setUsername(userVo.getUsername());
	    // 密碼加密
	    user.setPassword(passwordEncoder.encode(userVo.getPassword()));
	    user.setEmail(userVo.getEmail());
	    
	    user.setRole("USER");
	    
	    // Email 驗證預設
	    user.setVerified(false);
	    
	    // 設定預設頭像
	    user.setImgName("default-avatar.png");
	    
	    usersRepository.save(user);
	    
	    // 寄驗證信
	    emailService.sendEmail(
				user.getEmail() , 
				"Email Verification" , 
	    		"Please verify your email.");
	    
	    System.out.println("Service:"+ userVo);
	    System.out.println(passwordEncoder);
	    return true;
	}

	// 更新資料功能
	public void updateUser(String username , UserVo userVo) {
		
		UsersEntity user = usersRepository.findByUsername(username);
		
		user.setEmail(userVo.getEmail());
		
		usersRepository.save(user);
		
	}
	
}
