<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body class="no-overflow">
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <img id="bg-image" src="<c:url value="/static/img/bg.jpg"/>" alt="museum-bg">
</section>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
