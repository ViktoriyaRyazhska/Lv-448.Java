<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Add new exhibit</h2>
                <form action="<c:url value="/exhibits/add-exhibit"/>" method="post">
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
                               placeholder="Enter exhibit name">
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
                               placeholder="Enter exhibit technic">
                    </div>
                    <div class="form-group">
                        <label for="exhibit-audience">Audience</label>
                        <select class="browser-default custom-select" id="exhibit-audience" name="audience">
                            <c:forEach var="audience" items="${audiences}">
                                <option value="${audience.id}">${audience.name}</option>
                            </c:forEach>
                        </select>
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
