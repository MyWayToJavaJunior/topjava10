<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals list</title>
</head>
    <body>
        <h2><a href="index.html">Home</a></h2>
        <table border=1>
            <thead>
                <tr>
                    <th>Date Time</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th colspan="2">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${mealsList}" var="meal">
                    <tr>
                        <td><c:out value="${meal.dateTime}"/></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><c:out value="${meal.calories}"/></td>
                        <td>Update</td>
                        <td>Delete</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    <br>Add Meal</br>
    </body>
</html>