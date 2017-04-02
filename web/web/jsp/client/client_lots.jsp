<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your lots</title>
</head>
<body>
<h2>All your lots:</h2>
<ol>
    <c:forEach var="lot" items="${lotList}">
        <li>-----${lot.getName()}-----${lot.getDescription()}-------
            -----${lot.getQuantity()}------${lot.getCurrentPrice()}
            -----${lot.getMinPrice()}-------${lot.getStartPrice()}
            <a href="controller?command=deleteLot&id=${lot.getId()}">Delete Lot</a></li>
    </c:forEach>
</ol>
<p><a href="controller?command=back">Go Back</a></p>
</body>
</html>
