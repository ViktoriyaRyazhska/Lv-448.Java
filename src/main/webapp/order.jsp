<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Orders</title>
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
                        <label class="filter-col">Search:</label>
                    </div>
                    <form class="form-inline"
                          action="<c:url value="/orders_by_user"/>"
                          method="get"
                          id="by-user-phone">
                        <div class="form-group">
                            <input type="text" class="form-control input-xs" name="phone"
                                   placeholder="Enter user phone number">
                            <button type="submit" class="btn btn-primary">
                                Search
                            </button>
                        </div>
                    </form>
                    <div class="form-group">
                        <a class="btn btn-primary mr-2" href="<c:url value="/add-order"/>">Create</a>
                    </div>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="custom-list">
                <div class="card-columns">
                    <c:forEach var="order" items="${orders}">
                        <div class="card">
                            <div class="card-body">
                                <div class="card-title order-field-placeholder">Order S/N:
                                    <span class="order-card-field">${order.id}</span>
                                </div>
                                <hr>
                                <div class="card-title order-field-placeholder">User:
                                    <span class="order-card-field">${order.user.userName}</span>
                                    <span class="order-card-field">${order.user.userSurname}</span>
                                </div>
                                <hr>
                                <div class="card-title order-field-placeholder">Date order open:
                                    <span class="order-card-field">${order.dateOrder}</span>
                                </div>
                                <div class="card-title order-field-placeholder">Date order close:
                                    <span class="order-card-field">${order.dateReturn}</span>
                                </div>
                                <hr>
                                <div class="card-title order-field-placeholder">Book title
                                    <span class="order-card-field">${order.bookInstance.book.title} </span>
                                </div>
                                <div class="card-title order-field-placeholder">Book instance S/N:
                                    <span class="order-card-field">${order.bookInstance.id}</span>
                                </div>
                                <hr>


                                <form class="form-inline my-2 mx-2"
                                      action="<c:url value="/orderClose"/>"
                                      method="post"
                                      id="by-close-order">
                                    <input type="hidden" name="close" value="${order.id}">
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary">
                                            Close order
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