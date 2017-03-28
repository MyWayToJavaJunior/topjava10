<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <c:set var="dateTime" value="${meal.dateTime}" />
                    <tr ${meal.exceed ?  'style="color: red"' : 'style="color: green"'}>
                        <td>
                            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                            <fmt:formatDate value="${parsedDate}" pattern="dd.MM.yyyy HH:mm"/>
                        </td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><c:out value="${meal.calories}"/></td>
                        <td><a href="meals?action=update&id=<c:out value="${meal.id}"/>">Update</a></td>
                        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Delete</a> </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p><a href="meals?action=add">Add Meal</a> </p>
    </body>
</html>
