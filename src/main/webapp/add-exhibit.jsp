<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="notification-bar">
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                            ${successMessage}
                    </div>
                </c:if>
                <c:if test="${not empty failureMessage}">
                    <div class="alert alert-danger" role="alert">
                            ${failureMessage}
                    </div>
                </c:if>
            </div>
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Add new exhibit</h2>
                <form action="<c:url value="/exhibits/add-exhibit"/>" id="new-exhibit-form" method="post">
                    <div class="form-group">
                        <label for="exhibit-type">Type</label>
                        <select class="browser-default custom-select" id="exhibit-type" name="type">
                            <c:forEach var="type" items="${types}">
                                <option value="${type}">${type}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="exhibit-name">Name</label>
                        <input type="text" class="form-control" id="exhibit-name"
                               name="name"
                               placeholder="Enter exhibit name" required>
                    </div>
                    <div class="form-group">
                        <label for="exhibit-material">Material</label>
                        <input type="text" class="form-control" id="exhibit-material"
                               name="material"
                               placeholder="Enter exhibit material">
                    </div>
                    <div class="form-group">
                        <label for="exhibit-technic">Technic</label>
                        <input type="text" class="form-control" id="exhibit-technic"
                               name="technic"
                               placeholder="Enter exhibit technique">
                    </div>
                    <div class="form-group">
                        <label for="exhibit-audience">Audience</label>
                        <select class="browser-default custom-select" id="exhibit-audience" name="audience">
                            <c:forEach var="audience" items="${audiences}">
                                <option value="${audience.id}">${audience.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group author-group">
                        <div class="author-select-wrapper">
                            <label for="exhibit-author">Author(s)</label>
                            <select class="browser-default custom-select author-select" id="exhibit-author"
                                    name="author-1">
                                <c:forEach var="author" items="${authors}">
                                    <option value="${author.id}">${author.firstName} ${author.lastName}</option>
                                </c:forEach>
                            </select>
                            <div class="btn btn-dark ml-1 add-more-authors"><i class="fas fa-plus"></i></div>
                        </div>
                    </div>
                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
