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
<h2>Statistics</h2>

<div>
    <h3>Countries statistics</h3>
    <c:forEach var="country" items="${statisticCountry}">
        <p>${country.key} issued ${country.value} visas</p>
    </c:forEach>
</div>

<div>
    <h3>Statistics of ${user.firstName} ${user.lastName}</h3>
    <c:out value="${visasNumbers}"/>
    <p>${user.firstName} ${user.lastName} have ${visasNumbers} visas</p>
    <div>
        <ul>
            <c:forEach var="visited" items="${visitedCountries}">
                <li>
                    <c:out value="${visited.country}"/>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<div>
    <h3>Hotel statistics</h3>
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
        </c:forEach>
    </table>
    <h3>Room statistics</h3>
    <c:forEach var="hotel" items="${hotelsList}">
        <form action="/room_statistics/" method="GET">
            <fieldset>
                <legend>${hotel.hotelName}</legend>
                <c:out value="${hotel.hotelName}"/>
                <label>
                    Select start period: <input class="datepicker" type="text" name="start_period" required>
                </label>
                <label>
                    Select end period: <input class="datepicker" type="text" name="end_period" required>
                </label>
                <input hidden value="${hotel.hotelId}" name="hotel_id">
                <input hidden value="${hotel.hotelName}" name="hotel_name">
                <button class="submitBtn" type="submit">Show</button>
            </fieldset>
        </form>
    </c:forEach>
</div>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script >
    (function () {
        $('.datepicker').datepicker({
            maxDate: twoYearsFromNow()
        });

        function twoYearsFromNow() {
            let now = new Date();
            return new Date(now.getFullYear() + 2, now.getMonth(), now.getDate())
        }
    })();
</script>
</body>
</html>