<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div id="filter-panel" class="navbar bg-light rounded col-xl-12">
                <div class="form-inline">
                    <div class="form-group">
                        <label class="filter-col" for="exhibit-additional-filters">Filter by:</label>
                        <select class="browser-default custom-select" id="exhibit-additional-filters">
                            <option id="option-0" value="0" selected>Options...</option>
                            <option id="option-1" value="1">by Author</option>
                            <option id="option-2" value="2">by Employee</option>
                            <option id="option-3" value="3">by Audience</option>
                        </select>
                    </div> <!-- form group [employee-filters] -->
                    <form class="form-inline hidden" id="by-author" action="exhibits-by-author" method="post">
                        <div class="form-group">
                            <label class="filter-col" for="author-first-name">First name:</label>
                            <input type="text" class="form-control input-xs" id="author-first-name" name="firstName">
                            <label class="filter-col" for="author-last-name">Last name:</label>
                            <input type="text" class="form-control input-xs" id="author-last-name" name="lastName">
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [by-author] -->
                    </form>
                    <form class="form-inline hidden" id="by-employee">
                        <div class="form-group">
                            <label class="filter-col" for="employee-first-name">First name:</label>
                            <input type="text" class="form-control input-xs" id="employee-first-name">
                            <label class="filter-col" for="employee-last-name">Last name:</label>
                            <input type="text" class="form-control input-xs" id="employee-last-name">
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [by-employee] -->
                    </form>
                    <form class="form-inline hidden" id="by-audience">
                        <div class="form-group">
                            <label class="filter-col" for="audience-number">Number:</label>
                            <input type="number" class="form-control input-xs" id="audience-number">
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [by-audience] -->
                    </form>
                    <div class="form-group">
                        <a class="btn btn-dark mr-2" href="add-exhibit">New</a>
                        <a class="btn btn-dark" href="exhibit-statistics">Statistics</a>
                    </div> <!-- form group [buttons] -->
                </div>
            </div>
        </div> <!-- row [filters-panel] -->
        <div class="row">
            <div class="custom-list" id="excursions-list">
                <div class="card-columns">
                    <c:forEach var="exhibit" items="${exhibits}">
                        <div class="card">
                            <img class="card-img-top" src="static/img/exhibit-placeholder.jpg" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title">${exhibit.name}</h5>
                                <h6 class="card-title">Author name</h6>
                                <p class="card-text">
                                    <c:choose>
                                        <c:when test="${not empty exhibit.material}">
                                            ${exhibit.material}
                                        </c:when>
                                        <c:otherwise>
                                            ${exhibit.technique}
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                            </div>
                            <div class="card-footer text-right">
                                <a href="#" class="card-link">Update</a>
                                <a href="#" class="card-link">Remove</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="static/js/ui.js"></script>
</html>
