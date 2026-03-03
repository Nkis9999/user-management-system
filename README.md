# Spring Boot 會員管理系統

## 專案介紹
本專案為一個使用 Spring Boot + MySQL + Spring Security 開發的會員管理系統。

功能包含：
- 會員註冊
- 會員登入
- 登出功能
- 修改個人資料
- 修改密碼
- 忘記密碼
- 會員列表
- 搜尋功能
- 分頁系統
- 密碼加密儲存

## 使用技術
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- Thymeleaf
- Bootstrap

## 主要功能

### 會員註冊
使用者可以建立帳號並儲存至資料庫。

### 會員登入
使用 BCryptPasswordEncoder 驗證密碼。

### 個人資料
登入後可以修改 Username 與 Email。

### 修改密碼
驗證舊密碼後可修改新密碼。

### 忘記密碼
使用者可以重設密碼。

### 會員列表
可查看所有會員資料。

### 搜尋功能
可以依 Username 搜尋會員。

### 分頁功能
會員列表支援分頁顯示。

## 資料庫

users table:

id BIGINT  
username VARCHAR(50)  
password VARCHAR(255)  
email VARCHAR(100)

## 啟動方式

1. 建立資料庫 course

2. 修改 application.properties

3. 啟動 SpringBoot

4. 開啟瀏覽器

http://localhost:8080/login
