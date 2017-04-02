<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Buy lot page</title>
</head>
<body>
<h2>${lot.getName()}</h2>
<p> Description: ${lot.getDescription()}</p>
<p>Quantity: ${lot.getQuantity()}</p>
<p>Start Price: ${lot.getStartPrice()}</p>
<p>Current Price: ${lot.getCurrentPrice()}</p>
<form name="buyForm" method="POST" action="controller">
    <input type="hidden" name="command" value="buyLot"/>
    <input type="hidden" name="id" value="${lot.getId()}"/>
    <p>You buy: <input type="number" min="1" max="${lot.getQuantity()}" name="quantity"> </p>
    <input type="submit" value="Buy Now"/>
</form>
<p>${blankQuantity}</p>
<p><a href="controller?command=back">Go Back</a></p>
</body>
</html>
