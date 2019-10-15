<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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
            <div class="update-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Update employee</h2>
                <form action="update-employee/?id=${employee.id}" method="post">
                    <div class="form-group">
                        <label for="employee-firstname">Firstname</label>
                        <input type="text" class="form-control"
                               id="employee-firstname"
                               name="firstname"
                               value="${employee.firstName}"
                               placeholder="Enter firstname">
                    </div>
                    <div class="form-group">
                        <label for="employee-lastname">Lastname</label>
                        <input type="text" class="form-control"
                               id="employee-lastname"
                               value="${employee.lastName}"
                               name="lastname"
                               placeholder="Enter lastname">
                    </div>
                    <div class="form-group">
                        <label for="employee-username">Username</label>
                        <input type="text" class="form-control" id="employee-username"
                               aria-describedby="employee-username-help"
                               name="username"
                               value="${employee.login}"
                               placeholder="Enter username">
                        <small id="employee-username-help" class="form-text text-muted">Latin letters and numbers only</small>
                    </div>
                    <div class="form-group">
                        <label for="employee-password">Password</label>
                        <input type="password" class="form-control" id="employee-password"
                               aria-describedby="employee-password-help"
                               name="password"
                               value="${employee.password}"
                               placeholder="Enter password">
                        <small id="employee-password-help" class="form-text text-muted">Uppercase letters
                            and special symbols can significantly improve security</small>
                    </div>
                    <div class="form-group">
                        <label class="filter-col" for="employee-positions">Position</label>
                        <select class="browser-default custom-select" id="employee-positions" name="position">
                            <option id="option-0" value="NONE" selected>Positions...</option>
                            <option id="option-1" value="MANAGER">Manager</option>
                            <option id="option-2" value="AUDIENCE_MANAGER">Audience manager</option>
                            <option id="option-3" value="TOUR_GUIDE">Tour guide</option>
                        </select>
                    </div>
                    <div class="form-group" id="employee-is-am">
                        <label for="employee-audience">Audience</label>
                        <input type="number" class="form-control" id="employee-audience"
                               aria-describedby="employee-audience-help"
                               name="audience"
                               placeholder="Enter audience number">
                        <small id="employee-audience-help" class="form-text text-muted">Allowed only if employee's
                            position is an Audience manager</small>
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
