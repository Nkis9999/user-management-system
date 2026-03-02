# Spring Boot Member Login System

一個使用 Spring Boot + MySQL 開發的簡易會員登入系統，提供會員註冊與登入功能，並使用加密技術保護使用者密碼安全。

## 系統功能

- 會員註冊
- 會員登入
- 密碼加密 (BCrypt)
- MySQL資料庫存取

## 使用技術

Backend:
- Spring Boot
- Spring Security
- Spring Data JPA
- Lombok

Frontend:
- Thymeleaf
- Bootstrap

Database:
- MySQL

## 系統架構

Controller → Service → Repository → Database

## 執行方式

1. 建立資料庫

CREATE DATABASE course;

2. 建立 users 表

CREATE TABLE users (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(50),
 password VARCHAR(255),
 email VARCHAR(100),
 img_name VARCHAR(255)
);

3. 執行專案

http://localhost:8080
