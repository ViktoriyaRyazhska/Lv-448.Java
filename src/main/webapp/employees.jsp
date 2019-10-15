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
                        <label class="filter-col" for="employee-position">Position:</label>
                        <select class="browser-default custom-select" id="employee-position">
                            <option selected>Options...</option>
                            <option value="1">Manager</option>
                            <option value="2">Audience manager</option>
                            <option value="3">Tour guide</option>
                        </select>
                    </div> <!-- form group [employee-position] -->
                    <div class="form-group">
                        <button type="submit" class="btn btn-dark">
                            Filter
                        </button>
                        <form class="form-inline" action="<c:url value="/add-employee"/>" method="get">
                            <button type="submit" class="btn btn-dark">
                                New
                            </button>
                        </form>
                        <button type="submit" class="btn btn-dark">
                            Statistics
                        </button>
                    </div> <!-- form group [buttons] -->
                    <div class="form-group">
                        <label class="filter-col" for="employees-additional-filters">Additional:</label>
                        <select class="browser-default custom-select" id="employees-additional-filters">
                            <option id="option-0" value="0" selected>Filters...</option>
                            <option id="option-1" value="1">Free guides</option>
                        </select>
                    </div> <!-- form group [additional-filters] -->
                </div>
                <form class="form-inline additional-filter hidden" id="free-guides">
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
                    <button type="submit" class="btn btn-dark">
                        Show
                    </button>
                </form>
            </div>
        </div> <!-- row [filters-panel] -->
        <div class="row">
            <div class="container custom-list" id="employees-list">
                <table class="table table-striped">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Username</th>
                        <th scope="col">Position</th>
                        <th scope="col">Audience</th>
                        <th scope="col">Update</th>
                        <th scope="col">Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="employee" items="${employees}">
                        <tr>
                            <th scope="row">${employee.id}</th>
                            <td>${employee.firstName}</td>
                            <td>${employee.lastName}</td>
                            <td>${employee.login}</td>
                            <td>${employee.login}</td>
                            <td>1</td>
                            <td><i class="fas fa-pencil-alt"></i></td>
                            <td><i class="fas fa-trash-alt"></i></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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