<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Money</title>
</head>
<body>
<form name="lotCreation" method="POST" action="controller">
    <input type="hidden" name="command" value="${actionType}Money"/>
    <p>Enter amount of money:</p>
    <input type="number" min="1" name="amount" value="" size="20"/>
    <input type="submit" value="${buttonName}"/>
    </table>
</form>
<p><a href="controller?command=back">Go Back</a></p>
</body>
</html>
