<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.10/css/mdb.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div id="filter-panel" class="navbar bg-light rounded col-xl-12">
            <form class="form-inline" action="employee-statistics" method="post" role="form">
                <div class="form-group">
                    <label class="filter-col" for="date-time-from">Date/Time from:</label>
                    <input type="datetime-local" class="form-control input-xs w-200" name="dateTimeFrom" id="date-time-from">
                </div> <!-- form group [date-time-from] -->
                <div class="form-group">
                    <label class="filter-col" for="date-time-till">Date/Time till:</label>
                    <input type="datetime-local" class="form-control input-xs w-200" name="dateTimeTill" id="date-time-till">
                </div> <!-- form group [date-time-till] -->
                <button type="submit" class="btn btn-dark">
                    Filter
                </button>
            </form>
        </div>
    </div> <!-- row [filters-panel] -->
    <h1 class="text-center mt-4">Number of excursions by tour guide</h1>
    <canvas class="chart" id="tour-guide-excursions-total"></canvas>

    <h1 class="text-center mt-4">Number of minutes by tour guide</h1>
    <canvas class="chart" id="tour-guide-time-total"></canvas>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.10/js/mdb.min.js"></script>
<script src="static/js/ui.js"></script>

<script>
    <%--// Tour guides--%>
    <%--&lt;%&ndash;tourGuides = <%= tourGuides%>;&ndash;%&gt;--%>
    <%--// console.log(tourGuides);--%>

    <%--// Total number of excursions by tour guide--%>
    <%--&lt;%&ndash;excursionsTotal = <%= excursionsTotal%>;&ndash;%&gt;--%>
    <%--// console.log(excursionsTotal);--%>
    <%--new Chart(document.getElementById("tour-guide-excursions-total"), {--%>
    <%--    "type": "horizontalBar",--%>
    <%--    "data": {--%>
    <%--        "labels": tourGuides,--%>
    <%--        "datasets": [{--%>
    <%--            "label": "My First Dataset",--%>
    <%--            "data": excursionsTotal,--%>
    <%--            "fill": true,--%>
    <%--            "backgroundColor": ["rgba(255, 99, 132, 0.2)", "rgba(255, 159, 64, 0.2)",--%>
    <%--                "rgba(255, 205, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(54, 162, 235, 0.2)",--%>
    <%--                "rgba(153, 102, 255, 0.2)", "rgba(201, 203, 207, 0.2)"--%>
    <%--            ],--%>
    <%--            "borderColor": ["rgb(255, 99, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)",--%>
    <%--                "rgb(75, 192, 192)", "rgb(54, 162, 235)", "rgb(153, 102, 255)", "rgb(201, 203, 207)"--%>
    <%--            ],--%>
    <%--            "borderWidth": 1--%>
    <%--        }]--%>
    <%--    },--%>
    <%--    "options": {--%>
    <%--        "scales": {--%>
    <%--            "xAxes": [{--%>
    <%--                "ticks": {--%>
    <%--                    "beginAtZero": true--%>
    <%--                }--%>
    <%--            }]--%>
    <%--        }--%>
    <%--    }--%>
    <%--});--%>

    <%--// Total working time by tour guide--%>
    <%--&lt;%&ndash;timeTotal = <%= timeTotal%>;&ndash;%&gt;--%>
    <%--// console.log(timeTotal);--%>
    <%--new Chart(document.getElementById("tour-guide-time-total"), {--%>
    <%--    "type": "horizontalBar",--%>
    <%--    "data": {--%>
    <%--        "labels": tourGuides,--%>
    <%--        "datasets": [{--%>
    <%--            "label": "My First Dataset",--%>
    <%--            "data": timeTotal,--%>
    <%--            "fill": true,--%>
    <%--            "backgroundColor": ["rgba(255, 99, 132, 0.2)", "rgba(255, 159, 64, 0.2)",--%>
    <%--                "rgba(255, 205, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(54, 162, 235, 0.2)",--%>
    <%--                "rgba(153, 102, 255, 0.2)", "rgba(201, 203, 207, 0.2)"--%>
    <%--            ],--%>
    <%--            "borderColor": ["rgb(255, 99, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)",--%>
    <%--                "rgb(75, 192, 192)", "rgb(54, 162, 235)", "rgb(153, 102, 255)", "rgb(201, 203, 207)"--%>
    <%--            ],--%>
    <%--            "borderWidth": 1--%>
    <%--        }]--%>
    <%--    },--%>
    <%--    "options": {--%>
    <%--        "scales": {--%>
    <%--            "xAxes": [{--%>
    <%--                "ticks": {--%>
    <%--                    "beginAtZero": true--%>
    <%--                }--%>
    <%--            }]--%>
    <%--        }--%>
    <%--    }--%>
    <%--});--%>
</script>
</html>