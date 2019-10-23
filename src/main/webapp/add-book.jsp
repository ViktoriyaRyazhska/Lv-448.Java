<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Acer Laptop
  Date: 23.10.2019
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add-books</title>
</head>
<body>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Create new book</h2>
                <form action="<c:url value="/add-book"/>" method="post">

                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" class="form-control" id="title"
                               name="name"
                               placeholder="Enter book title">
                    </div>
                    <div class="form-group">
                        <label for="author-surname">Release date:</label>
                        <input type="text" class="form-control" id="author-surname"
                               name="releaseDate"
                               placeholder="Enter date release:">
                    </div>

                    <div class="form-group">
                        <label for="amountOfInstances">Amount of instances:</label>
                        <input type="text" class="form-control" id="amountOfInstances"
                               name="releaseDate"
                               placeholder="Enter date release:">
                    </div>

                    <div class="form-group">
                        <label for="author">Author:</label>
                        <input type="text" class="form-control" id="author"
                               name="releaseDate"
                               placeholder="Enter author:">
                    </div>

                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">Create</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
