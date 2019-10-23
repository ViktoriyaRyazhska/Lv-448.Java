<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>Authors</title>
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
                        <label class="filter-col">Search :</label>
                    </div>

                    <form class="form-inline "
                          action="<c:url value="/authors_by_surname"/>"
                          method="post"
                          id="by-author">
                        <div class="form-group">
                            <input type="text" class="form-control input-xs" id="author-last-name" name="surname"
                                   placeholder="Enter author surname">
                            <button type="submit" class="btn btn-primary">
                                Search
                            </button>
                        </div>
                    </form>

                    <div class="form-group">
                        <a class="btn btn-primary mr-2" href="<c:url value="/add-author"/>">Create</a>
                    </div>

                </div>
            </div>
        </div>


        <div class="row">
            <div class="custom-list">
                <div class="card-columns">
                    <c:forEach var="author" items="${authors}">
                        <div class="card">
                            <img class="card-img-top" src="<c:url value="/staticResources/img/author-placeholder.jpg"/>"
                                 alt="Card image cap">
                            <div class="card-body">
                                <h6>Name:
                                    <span class="card-author-title"> ${author.name}</span>
                                </h6>
                                <h6>
                                    Surname :
                                    <span class="card-author-title"> ${author.surname}</span>
                                </h6>
                                <hr>
                                <h6>Average age of readers: ${author.averageAgeUser}</h6>
                                <hr>
                                <div class="link-author-container">

                                    <div class="link-author">
                                        <a class="card-author-links"
                                           href="<c:url value="/books-by-author-id/${author.id}"/>" class="card-link">Books
                                            as the main author</a>
                                    </div>

                                    <div class="link-author">
                                        <a class="card-author-links"
                                           href="<c:url value="/books-by-co-author-id/${author.id}"/>"
                                           class="card-link">Books
                                            as the co-author</a>
                                    </div>

                                </div>

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
