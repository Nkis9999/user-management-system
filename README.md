# User Management System

A **User Management System** built with **Spring Boot, Spring Security, Thymeleaf, and MySQL**.
This project demonstrates a complete **CRUD user system with authentication, role-based authorization, and profile management**.

---

# 🚀 Features

* User Registration
* User Login / Logout
* Password Encryption (BCrypt)
* Role-Based Access Control

  * ROLE_USER
  * ROLE_ADMIN
  * ROLE_SUPER_ADMIN
* User Profile Management
* Profile Image Upload
* User CRUD (Create / Read / Update / Delete)
* Spring Security Authentication
* Thymeleaf Frontend UI
* MySQL Database Integration

---

# 🧰 Tech Stack

### Backend

* Java 17+
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Frontend

* Thymeleaf
* HTML / CSS
* Bootstrap

### Database

* MySQL

### Tools

* Git
* GitHub
* Maven

---

# 📂 Project Structure

```
user-management-system
│
├── controller
│   ├── LoginController
│   ├── UserController
│
├── service
│   ├── LoginService
│   ├── UserService
│
├── repository
│   ├── UserRepository
│
├── entity
│   ├── User
│
├── security
│   ├── SecurityConfig
│
├── templates
│   ├── login.html
│   ├── register.html
│   ├── profile.html
│
└── application.properties
```

---

# 🔐 Spring Security

This project uses **Spring Security** for authentication and authorization.

Security Features:

* Login Authentication
* Password Encryption using **BCrypt**
* Role-based access control
* Secure endpoints

Example:

```
ROLE_USER
ROLE_ADMIN
ROLE_SUPER_ADMIN
```

---

# 🗄 Database Schema

Example `users` table:

| id | username | password  | email                                   | img_name    |
| -- | -------- | --------- | --------------------------------------- | ----------- |
| 1  | admin    | encrypted | [admin@mail.com](mailto:admin@mail.com) | profile.jpg |

Password is stored using **BCrypt hashing**.

---

# 📦 Installation

### 1️⃣ Clone the repository

```
git clone https://github.com/Nkis9999/user-management-system.git
```

---

### 2️⃣ Open project

Open with:

* IntelliJ IDEA
* Eclipse
* VS Code

---

### 3️⃣ Configure Database

Edit:

```
application.properties
```

Example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

### 4️⃣ Run the project

Run:

```
SpringBootApplication
```

Then open:

```
http://localhost:8080
```

---

# 🖼 Screenshots

You can add screenshots here.

Example:

```
/images/login.png
/images/register.png
/images/profile.png
```

---

# 🎯 Future Improvements

* Admin Dashboard
* Pagination
* Search Users
* API version (REST API)
* JWT Authentication
* Docker Deployment

---

# 👨‍💻 Author

GitHub:
https://github.com/Nkis9999

---

# ⭐ If you like this project

Give it a **Star ⭐ on GitHub**!
