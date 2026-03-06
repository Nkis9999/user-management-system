package com.course.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.course.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

	List<UsersEntity> findByUsernameContaining(String username);
	
	Page<UsersEntity> findByUsernameContaining(String username,Pageable pageable);
	
	UsersEntity findByUsername(String username);
	
	UsersEntity findByVerificationToken(String token);
	
	UsersEntity findByEmail(String email);

}
