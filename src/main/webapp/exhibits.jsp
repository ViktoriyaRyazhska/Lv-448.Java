<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
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
                    <div class="form-group">
                        <label class="filter-col" for="exhibit-additional-filters">Filter by:</label>
                        <select class="browser-default custom-select" id="exhibit-additional-filters">
                            <option id="option-0" value="0" selected>Options...</option>
                            <option id="option-1" value="1">by Author</option>
                            <option id="option-2" value="2">by Employee</option>
                            <option id="option-3" value="3">by Audience</option>
                        </select>
                    </div> <!-- form group [employee-filters] -->
                    <form class="form-inline hidden"
                          action="<c:url value="/exhibits/by-author"/>"
                          method="post"
                          id="by-author">
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
                    <form class="form-inline hidden"
                          action="<c:url value="/exhibits/by-employee"/>"
                          method="post"
                          id="by-employee">
                        <div class="form-group">
                            <label class="filter-col" for="employee-first-name">First name:</label>
                            <input type="text" class="form-control input-xs" id="employee-first-name" name="firstName">
                            <label class="filter-col" for="employee-last-name">Last name:</label>
                            <input type="text" class="form-control input-xs" id="employee-last-name" name="lastName">
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [by-employee] -->
                    </form>
                    <form class="form-inline hidden"
                          action="<c:url value="/exhibits/by-audience"/>"
                          method="post"
                          id="by-audience">
                        <div class="form-group">
                            <label class="filter-col" for="audience">Filter by:</label>
                            <select class="browser-default custom-select" id="audience" name="audience">
                                <c:forEach var="audience" items="${audiences}">
                                    <option value="${audience.id}">${audience.name}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-dark">
                                Filter
                            </button>
                        </div> <!-- form group [by-audience] -->
                    </form>
                    <div class="form-group">
                        <a class="btn btn-dark mr-2" href="<c:url value="/exhibits"/>">Clear</a>
                        <a class="btn btn-dark mr-2" href="<c:url value="/exhibits/add-exhibit"/>">New</a>
                        <a class="btn btn-dark mr-2" href="<c:url value="/exhibits/add-author"/>">Add author</a>
                        <a class="btn btn-dark" href="<c:url value="/exhibits/statistics"/>">Statistics</a>
                    </div> <!-- form group [buttons] -->
                </div>
            </div>
        </div> <!-- row [filters-panel] -->
        <div class="row">
            <div class="custom-list" id="excursions-list">
                <div class="card-columns">
                    <c:forEach var="exhibit" items="${exhibits}">
                        <div class="card">
                            <img class="card-img-top" src="<c:url value="/static/img/exhibit-placeholder.jpg"/>" alt="Card image cap">
                            <div class="card-body">
                                <h4 class="card-title">${exhibit.name}</h4>
                                <h6 class="card-title">
                                    <span class="text-primary">Author(s): </span>
                                    <c:forEach var="author" items="${exhibit.authors}">
                                        <span>${author.firstName} ${author.lastName}</span>
                                    </c:forEach>
                                </h6>
                                <h6 class="card-title">
                                    <span class="text-primary">Audience: </span>
                                        ${exhibit.audience.name}</h6>
                                <h6 class="card-title">
                                    <c:choose>
                                        <c:when test="${not empty exhibit.material}">
                                            <span class="text-primary">Material: </span>
                                            ${exhibit.material}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-primary">Technique: </span>
                                            ${exhibit.technique}
                                        </c:otherwise>
                                    </c:choose>
                                </h6>
                            </div>
                            <div class="card-footer text-right">
                                <a href="<c:url value=""/>" class="card-link">Update</a>
                                <a href="<c:url value="/exhibits/delete-exhibit/${exhibit.id}"/>" class="card-link">Remove</a>
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
</html>
