<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">--%>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>add-books</title>
</head>
<body>
<section class="main-section">
    <div class="container" style="height: 100%; max-width: 40%">
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Create new book</h2>
                <form action="<c:url value="/add-book"/>" method="post">

                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" class="form-control" id="title"
                               name="title"
                               placeholder="Enter book title">
                    </div>

                    <div class="form-group">
                        <label for="author-surname">Release date</label>
                        <input type="date" class="form-control" id="author-surname"
                               name="releaseDate"
                               placeholder="Enter date release:" required>
                    </div>

                    <div class="form-group">
                        <label for="amountOfInstances">Amount of instances:</label>
                        <input type="number" class="form-control" id="amountOfInstances"
                               name="amountOfInstances"
                               placeholder="Enter amount of instances" required>
                    </div>

                    <div class="form-group">
                        <label>Choose author:</label>
                        <div>
                            <select class="browser-default selectpicker mt-3" name="author" required>
                                <c:forEach var="author" items="${authors}">
                                    <option value="${author.id}">${author.surname} ${author.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Choose co-author:</label>
                        <div>
                            <select class="browser-default selectpicker mt-3 co-authors" multiple name="co-author" required>
                                <c:forEach var="author" items="${authors}">
                                    <option value="${author.id}">${author.surname} ${author.name}</option>
                                </c:forEach>
                            </select>
                        </div>
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

<%--<script src="https://code.jquery.com/jquery-3.4.1.min.js"--%>
<%--        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>--%>
<%--<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>--%>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.4/js/bootstrap-select.min.js"></script>
<script src="<c:url value="/staticResources/js/dis.js"/>"></script>
</html>