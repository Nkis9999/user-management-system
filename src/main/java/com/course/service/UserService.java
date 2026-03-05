package com.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.course.entity.UsersEntity;
import com.course.repository.UsersRepository;

@Service
public class UserService {
	
	@Autowired
	UsersRepository usersRepository;
	
	public Page<UsersEntity> getUsers(int page){
		
		Pageable pageable = PageRequest.of(page, 5);
		
		return usersRepository.findAll(pageable);
		
	}
	
	public Page<UsersEntity> searchUsers(String keyword , int page){
		
		Pageable pageable = PageRequest.of(page, 5);
		
		return usersRepository.findByUsernameContaining(keyword, pageable);
		
	}
	
}
