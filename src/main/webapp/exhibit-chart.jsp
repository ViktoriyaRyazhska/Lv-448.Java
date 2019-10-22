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
        <h1 class="text-center mt-4">Sculptures by materials</h1>
        <% String materials = (String) request.getAttribute("materials"); %>
        <% String materialsQuantity = (String) request.getAttribute("materialsQuantity"); %>
        <canvas class="chart" id="materials-stats"></canvas>

        <h1 class="text-center mt-4">Paintings by technique</h1>
        <% String techniques = (String) request.getAttribute("techniques"); %>
        <% String techniquesQuantity = (String) request.getAttribute("techniquesQuantity"); %>
        <canvas class="chart" id="techniques-stats"></canvas>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script>
    // Materials of sculptures
    materials = <%= materials%>;
    console.log(materials);

    // Quantity of sculptures by material
    materialsQuantiy = <%= materialsQuantity%>;
    console.log(materialsQuantiy);
    new Chart(document.getElementById("materials-stats"), {
        "type": "horizontalBar",
        "data": {
            "labels": materials,
            "datasets": [{
                "label": "Exhibits count",
                "data": materialsQuantiy,
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

    // Techniques of paintings
    techniques = <%= techniques%>;
    console.log(techniques);

    // Quantity of paintings by technique
    techniquesQuantiy = <%= techniquesQuantity%>;
    console.log(materialsQuantiy);
    new Chart(document.getElementById("techniques-stats"), {
        "type": "horizontalBar",
        "data": {
            "labels": techniques,
            "datasets": [{
                "label": "Exhibits count",
                "data": techniquesQuantiy,
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