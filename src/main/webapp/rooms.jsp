<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Travel agency Valhalla</title>

</head>
<body>
    <form action="#" method="GET">
        <fieldset>
            <legend>Book a room!</legend>
<%--            <label>--%>
<%--                Select checkin: <input class="datepicker" type="text"/>--%>
<%--            </label>--%>
<%--            <br/>--%>
<%--            <label>--%>
<%--                Select checkout: <input class="datepicker" type="text"/>--%>
<%--            </label>--%>
            <c:forEach var="roomPojo" items="${roomsPojo}" varStatus="seq">
                <p>
                    <c:out value="${roomPojo.chamberNumber}"/>
                    <c:out value="${roomPojo.luxury}"/>
                    <c:out value="${roomPojo.bedrooms}"/>
                </p>
                <label>
                    Select checkin: <input class="${roomPojo.id}" type="text" onclick="unavailableDays(${roomPojo.id})"/>
                </label>
                <br/>
                <label>
                    Select checkout: <input class="${roomPojo.id}" type="text" onclick="unavailableDays(${roomPojo.id})"/>
                </label>
                <br/>
            </c:forEach>
        </fieldset>
    </form>


    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script>
        // TODO - add max date
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
            let bookedRooms = ${rooms};
            console.log(bookedRooms);
            bookedRooms.forEach(room => {
                period = function (from, to) {
                    let period = [];
                    let i = from;
                    while (i <= to){
                        period.push(i.yyyymmdd());
                        i = new Date(i.setDate(i.getDate() + 1));
                    }
                    return period;
                }(new Date(room.bookedFrom), new Date(room.bookedTo));
                if (room.room.id in periods){
                    periods[room.room.id] = periods[room.room.id].concat(period)
                } else {
                    periods[room.room.id] = period;
                }
            });

            for (let [key, value] of Object.entries(periods)) {
                console.log(key + ": \n");
                value.forEach(d => console.log(d + "\n"))
            }
        })();

        // $(document).ready(function(){
        //     $('.datepicker').datepicker({
        //         minDate: new Date(),
        //         beforeShowDay: function(date){
        //             let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
        //             return [ periods[1].indexOf(string) === -1 ]
        //         }
        //     });
        // });

        function unavailableDays(roomId) {
            $('.' + roomId).datepicker({
                minDate: new Date(),
                beforeShowDay: function(date){
                    let string = jQuery.datepicker.formatDate('yy-mm-dd', date);
                    return [ periods[roomId].indexOf(string) === -1 ]
                }
            });
        }
    </script>
</body>
</html>
