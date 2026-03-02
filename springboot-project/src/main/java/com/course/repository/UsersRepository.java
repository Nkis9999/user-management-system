package com.course.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.entity.UsersEntity;
import com.course.model.UserEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

//	List<UsersEntity> findByUsername(String username);
	
	Page<UserEntity> findByUsernameContaining(String username,Pageable pageable);
	
	UsersEntity findByUsername(String username);

//	UsersEntity findByEmail(String email);
	
}
