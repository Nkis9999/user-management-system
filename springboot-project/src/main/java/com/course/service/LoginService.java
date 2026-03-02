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

	    return result;
	}
	
	/**public boolean checkLogin(UserVo userVo) {
		
		UsersEntity user = usersRepository.findByUsername(userVo.getUsername());
//		UsersEntity user = usersRepository.findByEmail(userVo.getEmail());
		
		
		if(user == null) {
				return false;
			}
		
		return user.getPassword().equals(userVo.getPassword());
//		return passwordEncoder.matches(userVo.getPassword(), user.getPassword());
	}*/

	// 註冊
	public boolean registerUser(UserVo userVo) {

		// 改用 email 檢查
//		UsersEntity exisUser = usersRepository.findByEmail(userVo.getEmail());
		
		// 以 Username 去檢查
	    UsersEntity existUser = usersRepository.findByUsername(userVo.getUsername());
	    
	    if(existUser != null) {
	    	return false; //帳號已存在
	    }
	    
	    UsersEntity user = new UsersEntity();
	    
	    user.setUsername(userVo.getUsername());
	    user.setEmail(userVo.getEmail());

	    // 密碼加密
	    user.setPassword(passwordEncoder.encode(userVo.getPassword()));
	    usersRepository.save(user);
	    System.out.println("Service:"+ userVo);
	    System.out.println(passwordEncoder);
	    return true;
	}

}
