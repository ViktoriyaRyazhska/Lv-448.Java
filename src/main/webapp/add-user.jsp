<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/staticResources/css/style.css"/>">

    <title>add-user</title>
</head>
<body>
<section class="main-section">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="add-form col-xl-4 border rounded py-3">
                <h2 class="text-primary text-center">Create new user</h2>
                <form action="<c:url value="/add-user"/>" method="post">

                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" class="form-control" id="name"
                               name="name"
                               placeholder="Enter user name" required>
                    </div>

                    <div class="form-group">
                        <label for="surname">Surname:</label>
                        <input type="text" class="form-control" id="surname"
                               name="surname"
                               placeholder="Enter user surname" required>
                    </div>

                    <div class="form-group">
                        <label for="birthday">Birthday:</label>
                        <input type="date" class="form-control" id="birthday"
                               name="birthday"
                               placeholder="Enter user birthday" required>
                    </div>

                    <div class="form-group">
                        <label for="registrationDate">Registration date:</label>
                        <input type="date" class="form-control" id="registrationDate"
                               name="registrationDate"
                               placeholder="Enter user registration date" required>
                    </div>

                    <div class="form-group">
                        <label for="phoneNumber">Phone number:</label>
                        <input type="text" class="form-control" id="phoneNumber"
                               name="phoneNumber"
                               placeholder="Enter user phone number" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="text" class="form-control" id="email"
                               name="email"
                               placeholder="Enter user email" required>
                    </div>

                    <hr>

                    <div class="form-group">
                        <label for="city">City:</label>
                        <input type="text" class="form-control" id="city"
                               name="city"
                               placeholder="Enter user city" required>
                    </div>

                    <div class="form-group">
                        <label for="street">Street:</label>
                        <input type="text" class="form-control" id="street"
                               name="street"
                               placeholder="Enter user street" required>
                    </div>

                    <div class="form-group">
                        <label for="buildingNumber">Building number:</label>
                        <input type="text" class="form-control" id="buildingNumber"
                               name="buildingNumber"
                               placeholder="Enter user building number" required>
                    </div>

                    <div class="form-group">
                        <label for="apartment">Apartment:</label>
                        <input type="text" class="form-control" id="apartment"
                               name="apartment"
                               placeholder="Enter user apartment" required>
                    </div>
                    <div class="text-right">
                        <button type="submit" class="btn btn-primary">Create</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>
