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
<h2>We welcome you in Statistic</h2>

<div>
    <h3>Countries statistic</h3>
    <c:forEach var="country" items="${statisticCountry}">
        <h4>${country.key} issued ${country.value}</h4>
    </c:forEach>
</div>

<div>
    <h3>User statistic</h3>
    <c:out value="${statisticByUser}"/>
</div>

<div>
    <h3>Hotel statistic</h3>
    <table>
        <tr>
            <td>Hotel name</td>
            <td>Count clients</td>
            <td>Average Booking Time</td>
        </tr>
    <c:forEach var="hotel" items="${hotelsList}">
        <tr>
            <td><c:out value="${hotel.hotelName}"/></td>
            <td><c:out value="${hotel.clients}"/></td>
            <td><c:out value="${hotel.averageBookingTime.days}"/></td>
        </tr>
<%--        <h4>${country.hotelName}</h4>--%>
    </c:forEach>
    </table>
</div>

</body>
</html>
