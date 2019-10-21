<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hotel Page</title>
    <script>
        (function x() {
            var hotels = ${hotels};
            for (var i = 0; i < hotels.size; i++){
                console.log(hotels[i]);
            }
        })();
    </script>
</head>
<body>
    <h2>
        <c:out value="${city}"/>
    </h2>

    <c:forEach var="hotel" items="${hotels}" >
        <div>
            <a href="#">${hotel.hotelName}: </a><br/>
            <label>
                Select checkin date: <input type="date" name="datetimes"/>
            </label>
            <br>
            <label>
                Select checkout date: <input type="date" name="datetimes"/>
            </label>
        </div>
    </c:forEach>

    <script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <script src="js/date_picker.js"></script>
</body>
</html>