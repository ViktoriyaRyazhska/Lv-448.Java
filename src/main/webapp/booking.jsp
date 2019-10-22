<%@ page import="inc.softserve.entities.stats.RoomBooking" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<div>

    <% String bookings = (String) request.getAttribute("bookings");%>
</div>
</body>
<script>
    bookingsJson = <%= bookings%>
    console.log(bookingsJson.Lviv);

    console.log(bookingsJson.Lviv);

</script>
<c:out value="${bookingsJson.Lviv}"/>

</html>