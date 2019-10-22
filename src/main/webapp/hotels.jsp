<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Page</title>
</head>
<body>
    <h2>
        <c:out value="${city}"/>
    </h2>

    <c:if test="${empty hotels}">
        Sorry, we do not have partnership with this city
    </c:if>
    <c:forEach var="hotel" items="${hotels}" >
        <ul>
            <li>
                <a href="/rooms?hotelId=${hotel.id}">${hotel.hotelName}</a>
            </li>
        </ul>
    </c:forEach>


    <!-- TODO - redo URL mapping -->
    <form action="/available_hotels" method="GET">
        <fieldset>
            <legend>Search for available hotels during a period</legend>
            <label>
                Select checkin: <input type="date" name="checkin">
            </label>
            <br/>
            <label>
                Select checkout: <input type="date" name="checkout">
            </label>
            <br/>
            <input type="hidden" name="city_id" value="${cityId}">
            <button id="submit" type="submit">Search</button>
        </fieldset>
    </form>

    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<%--    <script src="js/date_picker.js"></script>--%>
</body>
</html>