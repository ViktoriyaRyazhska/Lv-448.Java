<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>User</title>
</head>
<jsp:include page="menu.jsp"/>
<body>


<div class="card mx-5 my-5 book-full-info-card" style="max-width: 100%;">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img class="card-img-top mx-3 my-3" src="<c:url value="/staticResources/img/user.jpg"/>"
                 alt="Card image cap">
        </div>
        <div class="col-md-8">
            <div class="card-body my-3 mx-3">
                <c:forEach var="user" items="${users}">
                    <h4 class="card-title">User name: ${user.name}</h4>
                    <h4 class="card-title">User surname: ${user.surname}</h4>
                    <h5>Birthday: ${user.birthday}</h5>
                    <h5>Phone number: ${user.phoneNumber}</h5>
                    <h5>Email: ${user.email}</h5>
                    <h5>Registration date: ${user.registrationDate}</h5>
                    <hr>
                    <h6>City: ${user.city}</h6>
                    <h6>Street: ${user.street}</h6>
                    <h6>Building number: ${user.buildingNumber}</h6>
                    <h6>Apartment: ${user.apartment}</h6>
                    <hr>
                    <h5>Time of using library: </h5>    <%-- TODO --%>;
                    <h5>List of books taken: </h5>    <%-- TODO --%>;
                    <h5>List of books in order: </h5>    <%-- TODO --%>;
                </c:forEach>
            </div>
        </div>
    </div>
</div>


</body>
</html>