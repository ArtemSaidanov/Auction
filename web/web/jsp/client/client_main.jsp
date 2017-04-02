<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Client Page</title>
</head>
<body>
<h1>Hello Client</h1>
<button onclick="location.href='controller?command=lotList'">Buy lot</button><br>
${lotBought}
${notEnoughMoney}
${lotDeleted}
${lotDeleteFailed}
<h4>Create New Lot:</h4>
<button onclick="location.href='controller?command=goToCreateLot'">Create lot</button><br>
${lotCreationFailed}
${lotCreated}
<h4>All your lots:</h4>
<button onclick="location.href='controller?command=goToClientLots'">Show all lots</button><br>
<p> </p>
<h4>On your account ${account.getAmountOfMoney()}</h4>
<table>
    <tr>
        <td><button onclick="location.href='controller?command=moneyPage&action=take'">Take money</button><br></td>
        <td><button onclick="location.href='controller?command=moneyPage&action=put'">Put money</button><br></td>
    </tr>
    <tr>
        ${moneySuccess}
        ${blankMoneyField}
    </tr>
</table>
<p> </p>
<a href="controller?command=logout">Logout</a>
</body>
</html>
