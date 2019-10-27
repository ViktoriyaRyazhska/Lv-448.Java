<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Blacklist</title>
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


            <div class="row">
                <div class="custom-list">
                    <div class="card-columns">
                        <c:forEach items="${map}" var="entry">
                            <div class="card">
                                <img class="card-img-top" src="<c:url value="/staticResources/img/user.jpg"/>"
                                     alt="Card image cap">
                                <div class="card-body">
                                    <h6>Name:
                                        <span> ${entry.key.name}</span>
                                    </h6>
                                    <h6>Name:
                                        <span> ${entry.key.surname}</span>
                                    </h6>
                                    <hr>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
