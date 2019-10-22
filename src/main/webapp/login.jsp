<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="./css/registration.css">
    <title>Login Page</title>
</head>
<body>
<div class="width30">
    <form action="login" method="POST">
            <div class="form-line">
                <label>
                    <span class="required-field-class" title="Required field">*</span>
                </label>
                <label>
                    <b>Email: </b><input type="text" placeholder="Enter Email" name="email" required>
                </label>
            </div>
            <div class="form-line">
                <label>
                    <span class="required-field-class" title="Required field">*</span>
                </label>
                <label>
                    <b>Password: </b><input type="password" placeholder="Enter Password" name="password" required>
                </label>
            </div>
        <button type="submit" class="registerbtn">Sign in</button>
        <div class="container signin">
            <p>Haven't created an account yet? <a href="registration">Sign up</a>.</p>
        </div>
    </form>
</div>
</body>
</html>