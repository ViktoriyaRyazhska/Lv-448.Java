<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <h1 class="text-primary">Best museum ever!</h1>
        </div>
    </div>
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</html>
