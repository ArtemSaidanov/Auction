<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form name="registrationForm" method="POST" action="controller">
    <input type="hidden" name="command" value="registration"/>
    Please, enter your data: <br/>
    <table>
        <tr>
            <td>Login:</td>
            <td><input type="text" name="login" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" value="" size="20"/></td>
        </tr>
        <tr>
            <td>First Name:</td>
            <td><input type="text" name="firstName" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Last Name:</td>
            <td><input type="text" name="lastName" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Amount of money:</td>
            <td><input type="number" min="1" name="amountOfMoney" value="" size="20"/></td>
        </tr>
    </table>
    ${operationMessage}
    ${errorUserExists} <br/>
    <input type="submit" value="Join Now"/>
    <a href="controller?command=back?page=login">Go Back</a>
</form>
</body>
</html>
