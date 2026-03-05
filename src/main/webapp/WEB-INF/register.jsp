<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>註冊</h1>

<form action="register" method="post" enctype="multipart/form-data">
	帳號:<input type="text" name="username" /><br/>
	密碼:<input type="text" name="password" /><br/>
	E-Mail:<input type="text" name="email" /><br/>
	上傳照片:<input type="file" name="photo" /><br/>
	<input type="submit" value="註冊" />
</form>

</body>
</html>