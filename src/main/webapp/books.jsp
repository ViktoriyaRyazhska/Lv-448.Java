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
                        <label class="filter-col">Search :</label>
                        <%--                        <select class="browser-default custom-select" id="exhibit-additional-filters">--%>
                        <%--                            <option id="option-0" value="0" selected>Options...</option>--%>
                        <%--                            <option id="option-1" value="1">Author Surname</option>--%>
                        <%--                            <option id="option-2" value="2">Employee</option>--%>
                        <%--                            <option id="option-3" value="3">Audience</option>--%>
                        <%--                        </select>--%>
                    </div> <!-- form group [employee-filters] -->


                    <form class="form-inline "
                          action="<c:url value="/by_title"/>"
                          method="post"
                          id="by-book">
                        <div class="form-group">
                            <%--                            <label class="filter-col" for="author-last-name" >Author Surname:</label>--%>
                            <input type="text" class="form-control input-xs" id="book-title" name="title"
                                   placeholder="Enter book title">
                            <button type="submit" class="btn btn-primary">
                                Search
                            </button>
                        </div> <!-- form group [by-author] -->
                    </form>


                    <%--                    <form class="form-inline hidden"--%>
                    <%--                          action="<c:url value="/exhibits/by-employee"/>"--%>
                    <%--                          method="post"--%>
                    <%--                          id="by-employee">--%>
                    <%--                        <div class="form-group">--%>
                    <%--                            <label class="filter-col" for="employee-first-name">First name:</label>--%>
                    <%--                            <input type="text" class="form-control input-xs" id="employee-first-name" name="firstName">--%>
                    <%--                            <label class="filter-col" for="employee-last-name">Last name:</label>--%>
                    <%--                            <input type="text" class="form-control input-xs" id="employee-last-name" name="lastName">--%>
                    <%--                            <button type="submit" class="btn btn-primary">--%>
                    <%--                                Filter--%>
                    <%--                            </button>--%>
                    <%--                        </div> <!-- form group [by-employee] -->--%>
                    <%--                    </form>--%>


                    <%--                    <form class="form-inline hidden"--%>
                    <%--                          action="<c:url value="/exhibits/by-audience"/>"--%>
                    <%--                          method="post"--%>
                    <%--                          id="by-audience">--%>
                    <%--                        <div class="form-group">--%>
                    <%--                            <label class="filter-col" for="audience">Filter by:</label>--%>
                    <%--                            &lt;%&ndash;                            <select class="browser-default custom-select" id="audience" name="audience">&ndash;%&gt;--%>
                    <%--                            &lt;%&ndash;                                <c:forEach var="audience" items="${audiences}">&ndash;%&gt;--%>
                    <%--                            &lt;%&ndash;                                    <option value="${audience.id}">${audience.name}</option>&ndash;%&gt;--%>
                    <%--                            &lt;%&ndash;                                </c:forEach>&ndash;%&gt;--%>
                    <%--                            &lt;%&ndash;                            </select>&ndash;%&gt;--%>
                    <%--                            <button type="submit" class="btn btn-primary">--%>
                    <%--                                Filter--%>
                    <%--                            </button>--%>
                    <%--                        </div> <!-- form group [by-audience] -->--%>
                    <%--                    </form>--%>
                    <div class="form-group">
                        <a class="btn btn-primary mr-2" href="<c:url value="/add-book"/>">Create</a>
                        <%--                        <a class="btn btn-primary" href="<c:url value="/exhibits/statistics"/>">Statistics</a>--%>
                    </div> <!-- form group [buttons] -->
                </div>
            </div>
        </div>
        <!-- row [filters-panel] -->


        <div class="row">
            <div class="custom-list" id="excursions-list">
                <div class="card-columns">
                    <c:forEach var="book" items="${books}">
                        <div class="card">
                                <%--                        <option value="${author.id}">${author.authorFirstName}, ${author.authorLastName}</option>--%>
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
                                    Average time reading:
                                    <span class="card-book-fs"> ${book.}</span>
                                </h6>

                                <h6>
                                    Author:
                                    <span class="card-book-fs"> ${book.getAuthor().getAuthorLastName()}</span>
                                    <span c>${book.getAuthor().getAuthorFirstName()}</span>
                                </h6>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
