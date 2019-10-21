<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/lib/jquery.skedTape.css"/>">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <% String excursions = (String) request.getAttribute("excursions"); %>
        <div id="excursion-schedule"></div>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="<c:url value="/static/js/lib/jquery.skedTape.js"/>"></script>
<%--<script src="<c:url value="/static/js/schedule.js"/>"></script>--%>
<script type="text/javascript">
    excursionsJson = <%= excursions%>;

    let excursionNames = [];
    excursionsJson.forEach(function (element) {
        excursionNames.push({id: element.id, name: element.excursionName});
    });

    function getUnique(arr, comp) {
        return arr
            .map(e => e[comp])
            .map((e, i, final) => final.indexOf(e) === i && i)
            .filter(e => arr[e]).map(e => arr[e]);
    }

    const uniqueExcursions = getUnique(excursionNames, "name");

    function getUniqueExcursionId(uniqueExcursions, name) {
        let id;
        id = uniqueExcursions.find(ex => ex.name === name).id;
        return id;
    }

    let excursions = [];

    excursionsJson.forEach(function (element) {
        excursions.push({
            name: element.tourGuideFullName,
            location: getUniqueExcursionId(uniqueExcursions, element.excursionName),
            start: new Date(element.dateStart),
            end: new Date(element.dateEnd)
        });
    });

    console.log(excursions);

    function getTimeLineStart(arr) {
        let start = new Date(arr[0].start);
        arr.forEach(function (element) {
            if (element.start < start) {
                start = element.start;
            }
        });
        return start;
    }

    function getTimeLineEnd(arr) {
        let end = new Date(arr[0].end);
        arr.forEach(function (element) {
            if (element.end > end) {
                end = element.end;
            }
        });
        return end;
    }

    const timeLineStart = getTimeLineStart(excursions);
    const timeLineEnd = getTimeLineEnd(excursions);

    /**
     * Visualize schedule
     */
    const mySchedule = $('#excursion-schedule').skedTape({
        caption: 'Excursions',
        start: timeLineStart,
        end: timeLineEnd,
        showEventTime: true,
        showEventDuration: true,
        scrollWithYWheel: true,
        locations: uniqueExcursions,
        events: excursions,
        formatters: {
            date: function (date) {
                return $.fn.skedTape.format.date(date, 'l', '.');
            },
            duration: function (start, end, opts) {
                return $.fn.skedTape.format.duration(start, end, {
                    hrs: 'г.',
                    min: 'хв.'
                });
            },
        }
    });
</script>
</html>
