<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Travel agency Valhalla</title>
</head>
<body>
    <h2>${message}</h2>
    <div>
        <a href="<c:url value="/index"/>">Go back to main menu: </a>
    </div>
</body>
</html>