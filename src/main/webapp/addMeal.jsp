<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 25.03.2017
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Meal</title>
</head>
    <body>
        <h2><a href="index.html">Home</a></h2>
        <form method="post" action="meals" name="meals">
            id: <input type="hidden"  name="id"
                      value="<c:out value="${meal.id}"/>"/><br/>
            dateTime:<input type="datetime-local" name="dateTime"
                            value="<c:out value="${meal.dateTime}"/>"/><br/>
            description:<input type="text" name="description"
                               value="<c:out value="${meal.description}"/>"/><br/>
            calories:<input type="number" name="calories"
                            value="<c:out value="${meal.description}"/>"/><br/>
            <input type="submit" value="submit"/>
        </form>
    </body>
</html>
