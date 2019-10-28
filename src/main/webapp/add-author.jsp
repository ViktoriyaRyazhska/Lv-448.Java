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
                <form action="<c:url value="/exhibits/add-author"/>" method="post">
                    <div class="form-group">
                        <label for="author-firstname">Fistname</label>
                        <input type="text" class="form-control" id="author-firstname"
                               name="firstname"
                               placeholder="Enter author's firstname" required>
                    </div>
                    <div class="form-group">
                        <label for="author-lastname">Lastname</label>
                        <input type="text" class="form-control" id="author-lastname"
                               name="lastname"
                               placeholder="Enter author's lastname" required>
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

