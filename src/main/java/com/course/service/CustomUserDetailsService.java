package com.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UsersEntity user =
                usersRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        // Email 未驗證就攔截不讓登入
        if(Boolean.FALSE.equals(user.getVerified())){
            throw new RuntimeException("Email not verified");
        }
        
        
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // USER ADMIN SUPER_ADMIN
                .build();
    }
	
}
