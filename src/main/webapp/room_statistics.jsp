<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Travel agency "Valhalla"</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="css/index.css">
</head>
<body>
    <h2>${hotel.name}</h2>
    <table>
        <tr>
            <td>Hotel name</td>
            <td>Chamber number</td>
            <td>Number of visitors</td>
        </tr>
        <c:forEach var="room" items="${room_stats}">
            <tr>
                <td><c:out value="${room.hotel.hotelName}"/></td>
                <td><c:out value="${room.chamberNumber}"/></td>
                <td><c:out value="${room.roomCount}"/></td>
            </tr>
            <%--        <h4>${country.hotelName}</h4>--%>
        </c:forEach>
    </table>

</body>
</html>