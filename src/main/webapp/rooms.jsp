<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Travel agency Valhalla</title>

</head>
<body>

    <c:forEach var="roomPojo" items="${roomsPojo}" varStatus="seq">
    <form action="/booking" method="GET">
        <fieldset>
            <legend>Book a room!</legend>
                <p>
                    <c:out value="${roomPojo.chamberNumber}"/>
                    <c:out value="${roomPojo.luxury}"/>
                    <c:out value="${roomPojo.bedrooms}"/>
                </p>
                <label>
                    Select checkin: <input class="${roomPojo.id}"
                                           type="text"
                                           name="checkin"
                                           onclick="unavailableDays(${roomPojo.id})"
                                           required/>
                </label>
                <br/>
                <label>
                    Select checkout: <input class="${roomPojo.id}"
                                            type="text"
                                            name="checkout"
                                            onclick="unavailableDays(${roomPojo.id})"
                                            required/>
                </label>
                <br/>
                <input type="hidden" name="hotel_id" value="${roomPojo.hotel.id}">
                <input type="hidden" name="room_id" value="${roomPojo.id}">
                <button id="submit" type="submit">Book!</button>
        </fieldset>
    </form>
    </c:forEach>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        // TODO - replace onclick with event lister
        // TODO - add validation that checkin is less or equal to checkout
        Date.prototype.yyyymmdd = function() {
            let mm = this.getMonth() + 1; // getMonth() is zero-based
            let dd = this.getDate();
            return [this.getFullYear(),
                (mm>9 ? '' : '0') + mm,
                (dd>9 ? '' : '0') + dd
            ].join('-');
        };

        let periods = {};
        let currentRoomId;
        (function () {
            let roomsInfo = ${roomsJson};
            console.log(roomsInfo);
            roomsInfo.forEach(room => {
                periods[room.id] = [];
                let period = [];
                room.bookings.forEach(booking => {
                    period = function (from, to) {
                        let period = [];
                        let i = from;
                        while (i <= to){
                            period.push(i.yyyymmdd());
                            i = new Date(i.setDate(i.getDate() + 1));
                        }
                        return period;
                    }(new Date(booking.bookedFrom), new Date(booking.bookedTill));
                    periods[room.id] = periods[room.id].concat(period);
                })
            });

            for (let [key, value] of Object.entries(periods)) {
                console.log(key + ": \n");
                value.forEach(d => console.log(d + "\n"))
            }
        })();

        function unavailableDays(roomId) {
            $('.' + roomId).datepicker({
                minDate: new Date(),
                maxDate: twoYearsFromNow(),
                beforeShowDay: function(date){
                    let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
                    return [ periods[roomId].indexOf(string) === -1 ]
                }
            });
        }

        function twoYearsFromNow() {
            let now = new Date();
            return new Date(now.getFullYear() + 2, now.getMonth(), now.getDate())
        }
    </script>
</body>
</html>
