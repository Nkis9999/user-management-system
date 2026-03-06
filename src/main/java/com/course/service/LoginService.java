package com.course.service;

import java.time.LocalDateTime;
import java.util.UUID;

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

	    System.out.println("username=" + userVo.getUsername());

	    System.out.println("password=" + userVo.getPassword());

	    UsersEntity user =
	        usersRepository.findByUsername(
	                userVo.getUsername());

	    if(user == null) {
	        System.out.println("查無帳號");
	        return false;
	    }
	    
	    // 先檢查 Email 是否驗證
	    if(!user.getVerified()) {
	    	System.out.println("Email 尚未驗證");
	    	return false;
	    }
	    
//	    System.out.println("DB password="
//	            + user.getPassword());

	    boolean result =
	        passwordEncoder.matches(
	            userVo.getPassword(),
	            user.getPassword());

	    System.out.println("matches="+result);
	    
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
	    
	    // 產生驗證 token
	    String token = UUID.randomUUID().toString();
	    user.setVerificationToken(token);
	    user.setTokenExpireTime(LocalDateTime.now().plusHours(1));
	    
	    // 設定預設頭像
	    user.setImgName("default-avatar.png");
	    
	    usersRepository.save(user);
	    
	    // 寄驗證信
	    String verifyLink = "http://localhost:8080/verify?token=" + token;

	    String html = """
	    <h2>Email Verification</h2>
	    <p>Please verify your email:</p>
	    <a href="%s">Click here to verify</a>
	    """.formatted(verifyLink);

	    emailService.sendEmail(
	            user.getEmail(),
	            "Email Verification",
	            html);
	    
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
	
	public boolean verifyEmail(String token) {
		
		UsersEntity user = usersRepository.findByVerificationToken(token);
		
		if(user == null) {
			return false;
		}
		// 檢查 token 是否過期
		if(user.getTokenExpireTime().isBefore(LocalDateTime.now())) {
			return false;
		}
		
		// 設定已驗證
		user.setVerified(true);
		
		user.setVerified(true);
		user.setVerificationToken(null);
		user.setTokenExpireTime(null);
		
		usersRepository.save(user);
		
		return true;
	}
	
	public void resendVerificationEmail(String email) {
		
		UsersEntity user = usersRepository.findByEmail(email);
		
		if(user == null) {
			return;
		}
		
		if(user.getVerified()) {
			return;
		}
		
		String token = UUID.randomUUID().toString();
		
		user.setVerificationToken(token);
		user.setTokenExpireTime(LocalDateTime.now().plusHours(1));
		
		usersRepository.save(user);
		
		String link = "http://localhost:8080/verify?token=" + token;

		String html = """
		<h2>Email Verification</h2>
		<p>Please verify your email:</p>
		<a href="%s">Click here to verify</a>
		""".formatted(link);

		emailService.sendEmail(
		        user.getEmail(),
		        "Email Verification",
		        html);
	};
	
}
