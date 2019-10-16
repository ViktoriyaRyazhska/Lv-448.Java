<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <title>Museum</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Add new excursion</h2>
                <form method="post" action="add-excursion">
                    <div class="form-group">
                        <label for="excursion-name">Name</label>
                        <input type="text" class="form-control" id="excursion-name"
                               name="name"
                               placeholder="Enter the name of excursion">
                    </div>
                    <div class="form-group">
                        <label for="excursion-datetime">Date/Time</label>
                        <input type="datetime-local" class="form-control" id="excursion-datetime"
                               name="dateTime"
                               placeholder="Enter date and time">
                    </div>
                    <div class="form-group">
                        <label for="excursion-duration">Duration</label>
                        <input type="text" class="form-control" id="excursion-duration"
                               aria-describedby="excursion-duration-help"
                               name="duration"
                               placeholder="Enter duration">
                        <small id="excursion-duration-help" class="form-text text-muted">Excursion duration in
                            minutes</small>
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
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script src="static/js/ui.js"></script>
</html>
