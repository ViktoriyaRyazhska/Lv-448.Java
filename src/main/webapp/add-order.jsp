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
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Create new book</h2>
                <form action="<c:url value="/add-order"/>" method="post">


                    <div class="form-group">
                        <label for="author-surname">Date order</label>
                        <input type="date" class="form-control" id="author-surname"
                               name="dateOrder"
                               placeholder="Enter date release:" required>
                    </div>
                    <hr>
                    <div class="form-group">
                        <div class="books-group">
                            <label>Choose book</label>
                            <select class="browser-default custom-select mt-3" id="select-book" name="book">
                                <c:forEach var="book" items="${books}">
                                    <option value="${book.id}">${book.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group">
                        <div class="users-group">
                            <label>Choose user</label>
                            <select class="browser-default custom-select mt-3" id="select-user" name="user">
                                <c:forEach var="user" items="${users}">
                                    <option value="${user.id}">${user.userName} ${user.userSurname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" class="form-control" id="title"
                               name="title"
                               placeholder="Enter book title" required>
                    </div>

                    <div class="form-group">
                        <label for="amountOfInstances">Amount of instances:</label>
                        <input type="number" class="form-control" id="amountOfInstances"
                               name="amountOfInstances"
                               placeholder="Enter amount of instances" required>
                    </div>


                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">Create</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="<c:url value="/staticResources/js/dis.js"/>"></script>
</body>
</html>
