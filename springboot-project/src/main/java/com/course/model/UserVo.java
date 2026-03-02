package com.course.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserVo {

    private String username;

    private String password;

    private String email;
    
    private String registeruser;
    
    private MultipartFile photo;

}