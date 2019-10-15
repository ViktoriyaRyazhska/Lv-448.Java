<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div id="filter-panel" class="navbar bg-light rounded col-xl-12">
                <form class="form-inline" role="form">
                    <div class="form-group">
                        <label class="filter-col" for="date-from">Date from:</label>
                        <input type="text" class="form-control input-xs" id="date-from">
                    </div> <!-- form group [date-from] -->
                    <div class="form-group">
                        <label class="filter-col" for="date-till">Date till:</label>
                        <input type="text" class="form-control input-xs" id="date-till">
                    </div> <!-- form group [date-till] -->
                    <div class="form-group">
                        <label class="filter-col" for="time-from">Time from:</label>
                        <input type="text" class="form-control input-xs" id="time-from">
                    </div> <!-- form group [time-from] -->
                    <div class="form-group">
                        <label class="filter-col" for="time-till">Time till:</label>
                        <input type="text" class="form-control input-xs" id="time-till">
                    </div> <!-- form group [time-till] -->
                    <div class="form-group">
                        <button type="submit" class="btn btn-dark">
                            Filter
                        </button>
                        <form action="<c:url value="/add-excursion"/>" method="get">
                            <button type="submit" class="btn btn-dark">
                                New
                            </button>
                        </form>
                        <button type="submit" class="btn btn-dark">
                            Statistics
                        </button>
                    </div> <!-- form group [buttons] -->
                </form>
            </div>
        </div> <!-- row [filters-panel] -->
        <div class="row">
            <div class="custom-list" id="excursions-list">
                <div class="card-columns">
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Excursion description text to build on the card title and make up the
                                bulk of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Excursion description text to build on the card title and make up the
                                bulk of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Excursion description text to build on the card title and make up the
                                bulk of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk
                                of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk
                                of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                    <div class="card">
                        <img class="card-img-top" src="static/img/excursion-placeholder.jpeg" alt="Card image cap">
                        <div class="card-body">
                            <h5 class="card-title">Excursion name</h5>
                            <p class="card-text">Some quick example text to build on the card title and make up the bulk
                                of the card's content.</p>
                        </div>
                        <div class="card-footer text-right">
                            <a href="#" class="card-link">Update</a>
                            <a href="#" class="card-link">Remove</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>