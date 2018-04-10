<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>登录页面</title>
</head>
<body>
<form action="/login/validate.do" method="post">
  <div>用户名：<input type="text" name="name"></div>
  <div>密码：<input type="password" name="password"></div>
  <div><button type="submit">提交</button></div>
</form>
</body>
</html>
