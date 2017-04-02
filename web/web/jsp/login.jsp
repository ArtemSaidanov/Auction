<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<form name="loginForm" method="POST" action="controller">
    <input type="hidden" name="command" value="login" />
    Please, enter your login and password: <br/>
    <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" size="20" /></td>
        </tr>
    </table>
    <p style="color: red">${errorLoginOrPassword} </p>
    <p>${operationMessage}</p>
    <input type="submit" value="Enter" />
    <a href="controller?command=gotoregistration">Registration</a>
</form>
</body>
</html>
