<%@ page import="inc.softserve.entities.stats.RoomBooking" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">

</head>
<body>
<div>

    <div>
        <c:forEach var="book" items="${bookings}" >
            <div>
                <span>${book}</span>
                <c:forEach var="book" items="${bookings}" >
                    <div>
                        <span>${book}</span>
                            <%--                <span>${book.room.hotel.hotelName}</span>--%>

                        <span> = </span>
                    </div>
                </c:forEach>
<%--                <span>${book.room.hotel.hotelName}</span>--%>

                <span> = </span>
            </div>
        </c:forEach>

    </div>

    <div class="width30">
        <form action="booking" method="post">
<%--            <select class="mdb-select md-form colorful-select dropdown-primary">--%>
<%--             <c:forEach var="book" items="${bookings}" >--%>
<%--                <option value=${book.room.hotel.id}>${book.room.hotel.hotelName}</option>--%>
<%--             </c:forEach>--%>
<%--            </select>--%>

<%--            <select class="mdb-select md-form colorful-select dropdown-primary">--%>
<%--                <c:forEach var="book" items="${bookings}" >--%>
<%--                    <option value=${book.room.id}>${book.room.chamberNumber}</option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>



<%--            <button type="submit" class="registerbtn">SAVE</button>--%>
<%--        </form>--%>
    </div>


</div>





            </body>
</html>