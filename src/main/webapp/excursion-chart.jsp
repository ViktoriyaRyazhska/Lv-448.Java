<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div id="filter-panel" class="navbar bg-light rounded col-xl-12">
            <form class="form-inline" action="<c:url value="/excursions/statistics"/>" method="post" role="form">
                <div class="form-group">
                    <label class="filter-col" for="date-time-from">Date/Time from:</label>
                    <input type="datetime-local" class="form-control input-xs w-250" name="from" id="date-time-from">
                </div> <!-- form group [date-time-from] -->
                <div class="form-group">
                    <label class="filter-col" for="date-time-till">Date/Time till:</label>
                    <input type="datetime-local" class="form-control input-xs w-250" name="till" id="date-time-till">
                </div> <!-- form group [date-time-till] -->
                <button type="submit" class="btn btn-dark">
                    Filter
                </button>
            </form>
        </div>
        <h1 class="text-center mt-4">Excursion statistics</h1>

        <% String statistics = (String) request.getAttribute("statistics"); %>
        <canvas class="chart" id="exhibit-stats"></canvas>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.8.10/js/mdb.min.js"></script>
<script src="<c:url value="/static/js/ui.js"/>"></script>

<script>
    // Excursion statistics
    statisticsJson = <%= statistics%>;
    console.log(statisticsJson);

    excursionNames = [];
    excursionCount = [];

    statisticsJson.forEach(function (element) {
        excursionNames.push(element.name);
        excursionCount.push(element.count);
    });

    console.log(excursionNames);
    console.log(excursionCount);


    new Chart(document.getElementById("exhibit-stats"), {
        "type": "horizontalBar",
        "data": {
            "labels": excursionNames,
            "datasets": [{
                "label": "Excursions count",
                "data": excursionCount,
                "fill": true,
                "backgroundColor": ["rgba(255, 99, 132, 0.2)", "rgba(255, 159, 64, 0.2)",
                    "rgba(255, 205, 86, 0.2)", "rgba(75, 192, 192, 0.2)", "rgba(54, 162, 235, 0.2)",
                    "rgba(153, 102, 255, 0.2)", "rgba(201, 203, 207, 0.2)"
                ],
                "borderColor": ["rgb(255, 99, 132)", "rgb(255, 159, 64)", "rgb(255, 205, 86)",
                    "rgb(75, 192, 192)", "rgb(54, 162, 235)", "rgb(153, 102, 255)", "rgb(201, 203, 207)"
                ],
                "borderWidth": 1
            }]
        },
        "options": {
            "scales": {
                "xAxes": [{
                    "ticks": {
                        "beginAtZero": true
                    }
                }]
            }
        }
    });
</script>
</html>