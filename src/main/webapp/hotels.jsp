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
                <a href="<c:url value="/rooms?hotelId=${hotel.id}"/>">${hotel.hotelName}</a>
            </li>
        </ul>
    </c:forEach>


    <c:if test="${not empty hotels}">
        <form action="<c:url value="/hotels/${city}"/>" method="GET">
            <fieldset>
                <legend>Search for available hotels during a period</legend>
                <label>
                    Select checkin: <input id="checkin" class="datepicker" type="text" name="checkin">
                </label>
                <br/>
                <label>
                    Select checkout: <input id="checkout" class="datepicker" type="text" name="checkout">
                </label>
                <br/>
                <input type="hidden" name="cityId" value="${cityId}">
                <button id="submitId" type="submit">Search</button>
            </fieldset>
        </form>
    </c:if>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="<c:url value="/js/forbid_illegal_time_period.js"/>"></script>
    <script>
        (function () {
            $('.datepicker').datepicker({
                minDate: new Date(),
                maxDate: twoYearsFromNow()
            });

            function twoYearsFromNow() {
                let now = new Date();
                return new Date(now.getFullYear() + 2, now.getMonth(), now.getDate());
            }
        })();
    </script>
</body>
</html>