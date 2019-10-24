<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/>">
    <title>Museum</title>
</head>
<body>
<section class="main-section">
    <div class="container">
        <div class="row">
            <div class="col-xl-12 mt-30">
                <h2 class="error-code text-danger text-center">500</h2>
                <h2 class="text-dark text-center mt-2">
                    Woops! We are so sorry, but we did something wrong.
                </h2>
                <h2 class="text-dark text-center mt-2">
                    Please, forgive us!
                </h2>
                <div class="text-center mt-3">
                    <a href="<c:url value="/"/>">Return to homepage</a>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>