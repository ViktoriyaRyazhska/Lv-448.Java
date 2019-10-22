<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/registration.css">
    <title>Registration Page</title>
</head>
<body>
<div class="width30">
<form action="registration" method="POST" name="registrform" >
    <div class="container">
        <h1>Register</h1>
            <p>Please fill in this form to create an account.</p>
        <hr>
        <div class="form-line">
            <label>
                <b>First Name: <input type="text" placeholder="Enter First Name:" name="firstname" required></b>
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Last Name: <input type="text" placeholder="Enter Last Name:" name="lastname" required></b>
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Email <input type="text" placeholder="Enter Email" name="email" required></b>
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Password: </b><input type="password" placeholder="Enter Password" name="password" required>
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Date Birthday: </b><input type="text" placeholder="YYYY/MM/DD" name="date" required>
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Phone number: </b><input type="text" placeholder="Phone number" name="phone" required>
            </label>
        </div>
        <hr>
        <div class="form-line">
            <label>
                <b>Visa Number: </b><input type="text" placeholder="Number Visa" name="numbervisa">
            </label>
        </div>
        <div class="form-line">
            <label><b>Issued: </b><input type="text" placeholder="YYYY/MM/DD" name="start"></label>
        </div>
        <div class="form-line">
            <label>
                <b>Expiration: </b> <input type="text" placeholder="YYYY/MM/DD" name="end">
            </label>
        </div>
        <div class="form-line">
            <label>
                <b>Country: </b><input type="text" placeholder="Country" name="country">
            </label>
        </div>
        <button type="submit" class="registerbtn">Register</button>
    </div>
    <div class="container signin">
        <p>Already have an account? <a href="login.jsp">Sign in</a>.</p>
    </div>
</form>
    </div>
</body>
</html>

