<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Lot</title>
</head>
<body>
<h2>Fill out the form to create a lot</h2>
<h4>*all fields are required</h4>
<form name="lotCreation" method="POST" action="controller">
    <input type="hidden" name="command" value="createLot"/>
    <table>
        <tr>
            <td>Name of lot:</td>
            <td><input type="text" name="name" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Description :</td>
            <td><input type="text" name="description" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Quantity:</td>
            <td><input type="number" min="1" name="quantity" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Start price, $:</td>
            <td><input type="number" min="10" name="startPrice" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Minimal price, $:</td>
            <td><input type="number" min="1" name="minPrice" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Price reduction step, %:</td>
            <td><input type="number" min="5" name="priceCutStep" value="" size="20"/></td>
        </tr>
        <tr>
            <td>Time to next price reduction, min:</td>
            <td><input type="number" min="1" name="timeToNextCut" value="" size="20"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Create Lot" size="20"/></td>
        </tr>
    </table>
</form>
<p><a href="controller?command=back">Go Back</a></p>
</body>
</html>
