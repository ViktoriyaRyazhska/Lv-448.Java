<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Book</title>
</head>
<jsp:include page="menu.jsp"/>
<body>


<div class="card mx-5 my-5 book-full-info-card" style="max-width: 100%;">
    <div class="row no-gutters">
        <div class="col-md-4">
            <img class="card-img-top mx-3 my-3" src="<c:url value="/staticResources/img/book-img.jpg"/>"
                 alt="Card image cap">
        </div>
        <div class="col-md-8">
            <div class="card-body my-3 mx-3">
                <c:forEach var="book" items="${books}">

                    <h4 class="card-title">Title: ${book.title}</h4>

                    <h5>Amount of instances: ${book.amountOfInstances}</h5>
                    <h5>Release date: ${book.releaseDate}</h5>
                    <h5>
                        Author:
                        <span>${book.author.authorFirstName}</span>
                        <span> ${book.author.authorLastName}</span>
                    </h5>
                    <hr>

                    <h5>
                        Co-author:
                        <c:forEach var="subAuthor" items="${book.subAuthors}">
                            <h6>${subAuthor.name} ${subAuthor.surname}</h6>
                        </c:forEach>
                    </h5>

                    <hr>
                    <h6>
                        Was taken:
                        <span> ${book.amountOfTimesBookWasTaken} time(s)</span>
                    </h6>
                    <h6>
                        Average time reading:
                        <span>${book.averageTimeReading}</span>
                    </h6>
                    <h6>
                        Average age of readers:
                        <span>${book.averageAgeUsers}</span>
                    </h6>
                    <h6>Availability:
                            ${book.isAvailable}
                    </h6>
                    <hr>
                    <div class="instance-table">
                        <h5>Book instances information</h5>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th scope="col">Instance S/N</th>
                                <th scope="col">Available</th>
                                <th scope="col">Taken time(s)</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="bookInstance" items="${book.bookInstance}">
                                <tr>
                                    <td>${bookInstance.id}</td>
                                    <td>
                                        <c:if test="${bookInstance.isAvailable}">Yes</c:if>
                                        <c:if test="${not bookInstance.isAvailable}">No</c:if>
                                    </td>
                                    <td>${bookInstance.countWasTaken}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


</body>
</html>
