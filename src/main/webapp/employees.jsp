<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div class="notification-bar">
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                            ${successMessage}
                    </div>
                </c:if>
                <c:if test="${not empty failureMessage}">
                    <div class="alert alert-danger" role="alert">
                            ${failureMessage}
                    </div>
                </c:if>
            </div>
            <div id="filter-panel" class="navbar bg-light rounded col-xl-12">
                <div class="form-inline">
                    <form action="<c:url value="/employees/by-position"/>" method="post">
                        <div class="form-group">
                            <label class="filter-col" for="employee-position">Position:</label>
                            <select class="browser-default custom-select" id="employee-position" name="position">
                                <option value="NONE" selected>All</option>
                                <option value="MANAGER">Manager</option>
                                <option value="AUDIENCE_MANAGER">Audience manager</option>
                                <option value="TOUR_GUIDE">Tour guide</option>
                            </select>
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [employee-position] -->
                    </form>
                    <div class="form-group">
                        <a class="btn btn-dark mr-2" href="<c:url value="/employees/add-employee"/>">New</a>
                        <a class="btn btn-dark" href="<c:url value="/employees/statistics"/>">Statistics</a>
                    </div> <!-- [buttons] -->
                    <div class="form-group">
                        <label class="filter-col" for="employees-additional-filters">Additional:</label>
                        <select class="browser-default custom-select" id="employees-additional-filters">
                            <option id="option-0" value="0" selected>Filters...</option>
                            <option id="option-1" value="1">Free guides</option>
                        </select>
                    </div> <!-- form group [additional-filters] -->
                </div>
                <form action="<c:url value="/employees/free-guides"/>"
                      method="post"
                      class="form-inline additional-filter hidden"
                      id="free-guides" >
                    <div class="form-group">
                        <label class="filter-col" for="date-time-from">Date/Time from:</label>
                        <input type="datetime-local" class="form-control input-xs w-250" id="date-time-from" name="from">
                    </div> <!-- form group [date-from] -->
                    <div class="form-group">
                        <label class="filter-col" for="date-time-till">Date/Time till:</label>
                        <input type="datetime-local" class="form-control input-xs w-250" id="date-time-till" name="till">
                    </div> <!-- form group [date-till] -->
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
                            <td>${employee.position}</td>
                            <td>
                                    <c:choose>
                                        <c:when test="${not empty employee.audience}">
                                            ${employee.audience.name}
                                        </c:when>
                                        <c:otherwise>
                                            none
                                        </c:otherwise>
                                    </c:choose></td>
                            <td>
                                <a class="text-dark" href="<c:url value="/employees/update-employee/${employee.id}"/>">
                                    <i class="fas fa-pencil-alt"></i>
                                </a>
                            </td>
                            <td>
                                <a class="text-dark" href="<c:url value="/employees/delete-employee/${employee.id}"/>">
                                    <i class="fas fa-trash-alt"></i>
                                </a>
                            </td>
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
</html>