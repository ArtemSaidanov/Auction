<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lot List</title>
</head>
<body>
<c:forEach var="lot" items="${lotList}">
    -----${lot.getName()}-----${lot.getQuantity()}------${lot.getCurrentPrice()}
        <button onclick="location.href='controller?command=showLot&id=${lot.getId()}'">Show lot</button><br>
</c:forEach>
<p><a href="controller?command=back">Go Back</a></p>
</body>
</html>

