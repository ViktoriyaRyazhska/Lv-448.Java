<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Books</title>
</head>
<jsp:include page="menu.jsp"/>
<body>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div class="notification-bar">
                <%--                <c:if test="${not empty successMessage}">--%>
                <%--                    <div class="alert alert-success" role="alert">--%>
                <%--                            ${successMessage}--%>
                <%--                    </div>--%>
                <%--                </c:if>--%>
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
                            <option id="option-1" value="1">Title</option>
                            <option id="option-2" value="2">Independence</option>
                            <option id="option-3" value="3">by Audience</option>
                        </select>
                    </div> <!-- form group [employee-filters] -->
                    <form class="form-inline hidden"
                          action="<c:url value="/by_title"/>"
                          method="post"
                          id="by-title">
                        <div class="form-group">
                            <input type="text" class="form-control input-xs" id="title-book" name="title" placeholder="Please enter book title">
                            <button type="submit" class="btn btn-primary">
                                Search
                            </button>
                        </div>
                    </form>

                    <form class="form-inline hidden"
                          action="<c:url value="/"/>"
                          method="post"
                          id="by-independence">
                        <div class="form-group">
                            <label class="filter-col" for="independence-date-from">Date from:</label>
                            <input type="date" class="form-control input-xs" id="independence-date-from" name="dateFrom">
                            <label class="filter-col" for="independence-date-to">Date to:</label>
                            <input type="date" class="form-control input-xs" id="independence-date-to" name="dateTo">
                            <button type="submit" class="btn btn-primary">
                                Search
                            </button>
                        </div>
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
                        <a class="btn btn-primary mr-2" href="<c:url value="/add-order"/>">Create</a>
<%--                        <a class="btn btn-primary mr2" href="<c:url value="/exhibits/statistics"/>">Statistics</a>--%>
                    </div> <!-- form group [buttons] -->
                </div>
            </div>
        </div> <!-- row [filters-panel] -->
<%--                    <form class="form-inline "--%>
<%--                          action="<c:url value="/by_title"/>"--%>
<%--                          method="post"--%>
<%--                          id="by-book">--%>
<%--                        <div class="form-group">--%>
<%--                            <label class="filter-col" for="author-last-name">Author Surname:</label>--%>
<%--                            <input type="text" class="form-control input-xs" id="book-title" name="title"--%>
<%--                                   placeholder="Enter book title">--%>
<%--                            <button type="submit" class="btn btn-primary">--%>
<%--                                Search--%>
<%--                            </button>--%>
<%--                        </div> <!-- form group [by-author] -->--%>
<%--                    </form>--%>





        <div class="row">
            <div class="custom-list">
                <div class="card-columns">
                    <c:forEach var="book" items="${books}">
                        <div class="card">
                            <img class="card-img-top" src="<c:url value="/staticResources/img/book-img.jpg"/>"
                                 alt="Card image cap">
                            <div class="card-body">
                                <h6>Title:
                                    <span class="card-author-title"> ${book.title}</span>
                                </h6>
                                <h6>
                                    Amount of instances:
                                    <span class="card-book-fs"> ${book.amountOfInstances}</span>
                                </h6>
                                <h6>
                                    Release date:
                                    <span class="card-book-fs"> ${book.releaseDate}</span>
                                </h6>
                                <hr>
                                <h6>
                                    Author:
                                    <span> ${book.author.authorLastName}</span>
                                    <span>${book.author.authorFirstName}</span>
                                </h6>
                                <hr>
                                <h6>
                                    Was taken:
                                    <span class="card-book-fs"> ${book.amountOfTimesBookWasTaken} time(s)</span>
                                </h6>

                                <h6>
                                    Average time reading:
                                    <h6>
                                    <span class="card-book-fs">${book.averageTimeReading}</span>
                                    </h6>
                                    </h6>
                                <hr>

                                Availability:
                                <span>${book.isAvailable}</span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="<c:url value="/staticResources/js/dis.js"/>"></script>
</html>
