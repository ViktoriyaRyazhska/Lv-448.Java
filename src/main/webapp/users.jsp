
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Users</title>
</head>
<jsp:include page="menu.jsp"/>
<body>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div class="notification-bar">
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
                            <option id="option-1" value="1">Average Age of Users</option>
                            <option id="option-2" value="2">Average amount of orders by some period</option>
                            <option id="option-3" value="3">Average time of using library</option>
                            <option id="option-4" value="4">Get black list</option>
                        </select>
                    </div>

                    <%-- TODO form 1 /averageAgeOfUser --%>;

                    form class="form-inline hidden"
                    action="<c:url value="/averageAmountOfOrdersBySomePeriod"/>"
                    method="post"
                    id="by-period">
                    <div class="form-group">
                        <label class="filter-col" for="period-date-from">Date from:</label>
                        <input type="date" class="form-control input-xs" id="period-date-from"
                               name="fromDate">
                        <label class="filter-col" for="period-date-to">Date to:</label>
                        <input type="date" class="form-control input-xs" id="period-date-to" name="toDate">
                        <button type="submit" class="btn btn-primary">
                            Search
                        </button>
                    </div>


                    <%-- TODO form 3 /averageTimeOfUsingLibrary --%>;
                    <%-- TODO form 4 /blacklist --%>;

                    <div class="form-group">
                        <a class="btn btn-primary mr-2" href="<c:url value="/add-user"/>">Create</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="custom-list">
                <div class="card-columns">
                    <c:forEach var="user" items="${users}">
                        <div class="card">
                            <img class="card-img-top" src="<c:url value="/staticResources/img/user.jpg"/>"
                                 alt="Card image cap">
                            <div class="card-body">
                                <h6> Name:
                                    <span> ${user.name}</span>
                                </h6>
                                <h6>
                                    Surname:
                                    <span> ${user.surname}</span>
                                </h6>
                                <form class="form-inline my-2 mx-2"
                                      action="<c:url value="/user-by-id.jsp"/>"
                                      method="get"
                                      id="id">
                                    <input type="hidden" name="id" value="${user.id}">
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">
                                            Full information
                                        </button>
                                    </div>
                                </form>
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
