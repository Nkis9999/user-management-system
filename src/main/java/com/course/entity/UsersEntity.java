package com.course.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UsersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	@Column(name = "img_name")
	private String imgName;

	@Column(name = "role")
	private String role;

	public String getAvatarUrl(){

	    if(imgName == null 
	        || imgName.isEmpty() 
	        || imgName.equals("default-avatar.png")){

	        return "/images/default-avatar.png";
	    }

	    return "/upload/" + imgName;
	}

}



