<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Travel agency Valhalla</title>
    <script>
        (function () {
                console.log(${rooms});
            }
        )();

        <%--let rooms = ${rooms};--%>
        <%--let bookedRooms = [];--%>
        <%--rooms.forEach(function (element) {--%>
        <%--   bookedRooms.push({element.room.id});--%>
        <%--});--%>
    </script>
</head>
<body>
    <form action="#" method="GET">
        <fieldset>
            <legend>Book a room!</legend>
            <c:forEach var="room" items="${rooms}" >
                <label>
                    # = ${room.room.chamberNumber}, starts = ${room.room.luxury}, bedrooms = ${room.room.bedrooms}
                    <input type="date" name="datetimes"/>
                </label>
            </c:forEach>
        </fieldset>
    </form>
</body>
</html>
